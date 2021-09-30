package com.example.controller

import com.example.dto.ItemCreateRequestDto
import com.example.dto.ItemResponseDto
import com.example.service.RSocketService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
class RSocketServerController(
    private val service: RSocketService
) {

    @MessageMapping("items.saveRequestResponse")
    fun saveNewItemViaRSocketRequestResponse(item: ItemCreateRequestDto): Mono<ItemResponseDto> {
        return service.saveItemInRequestResponse(item)
    }

    @MessageMapping("items.getRequestStream")
    fun getItemsViaRSocketRequestStream(): Flux<ItemResponseDto> {
        return service.getItemsInRequestStream()
    }

    @MessageMapping("items.saveWithoutResponse")
    fun processNewItemsViaRSocketFireAndForget(item: ItemCreateRequestDto): Mono<Void> {
        return service.saveItemInFireAndForget(item)
    }

    @MessageMapping("items.monitor")
    fun monitorNewItems(): Flux<ItemResponseDto> {
        return service.getItemsMonitor()
    }

}