package com.example.konstantin.weartest.gateway

/**
 * @author Kulikov Konstantin
 * @since 19.01.2018.
 */

abstract class WearableException: Exception()

class DataItemNotFound: WearableException()