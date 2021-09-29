package com.example.service

import com.example.domain.Item
import com.example.domain.ItemRepository
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Sinks

@Service
class RSocketService(
    private val itemRepository: ItemRepository
) {

    private val itemsSink: Sinks.Many<Item> = Sinks.many().multicast().onBackpressureBuffer()

    @MessageMapping("newItems.request-response")
    fun processNewItemsViaRSocketRequestResponse(item: Item): Mono<Item> {
        return itemRepository.save(item)
            .doOnNext { savedItem -> itemsSink.tryEmitNext(savedItem)}
    }

    @MessageMapping("newItems.request-stream")
    fun findItemsViaRSocketRequestStream(): Flux<Item> {
        return itemRepository.findAll()
            .doOnNext(itemsSink::tryEmitNext)
    }

    @MessageMapping("newItems.fire-and-forget")
    fun processNewItemsViaRSocketFireAndForget(item: Item): Mono<Void> {
        return itemRepository.save(item)
            .doOnNext { savedItem -> itemsSink.tryEmitNext(savedItem) }
            .then()
    }

    @MessageMapping("newItems.monitor")
    fun monitorNewItems(): Flux<Item> {
        return this.itemsSink.asFlux()
    }
}
