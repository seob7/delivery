package com.delivery.common.exception

import com.delivery.common.error.ErrorCodeIfs

interface ApiExceptionIfs {

    val errorCodeIfs: ErrorCodeIfs?
    val errorDescription: String?

}