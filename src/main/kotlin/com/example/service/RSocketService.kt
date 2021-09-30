package com.example.service

import com.example.domain.ItemRepository
import com.example.dto.ItemCreateRequestDto
import com.example.dto.ItemResponseDto
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Sinks

@Service
class RSocketService(
    private val itemRepository: ItemRepository
) {

    private val itemsSink: Sinks.Many<ItemResponseDto> = Sinks.many().multicast().onBackpressureBuffer()

    fun saveItemInRequestResponse(item: ItemCreateRequestDto): Mono<ItemResponseDto> {
        return itemRepository.save(item.toEntity())
            .map { savedItem -> savedItem.toResponseDto() }
            .doOnNext { savedItem -> itemsSink.tryEmitNext(savedItem)}
    }

    fun getItemsInRequestStream(): Flux<ItemResponseDto> {
        return itemRepository.findAll()
            .map { foundItem -> foundItem.toResponseDto() }
            .doOnNext(itemsSink::tryEmitNext)
    }

    fun saveItemInFireAndForget(item: ItemCreateRequestDto): Mono<Void> {
        return itemRepository.save(item.toEntity())
            .map { savedItem -> savedItem.toResponseDto() }
            .doOnNext { savedItem -> itemsSink.tryEmitNext(savedItem) }
            .then()
    }

    fun getItemsMonitor(): Flux<ItemResponseDto> {
        return itemsSink.asFlux()
    }
}
