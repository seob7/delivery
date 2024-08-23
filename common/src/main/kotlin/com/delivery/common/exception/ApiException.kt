package com.delivery.common.exception

import com.delivery.common.error.ErrorCodeIfs
import jdk.jfr.Description

class ApiException : RuntimeException, ApiExceptionIfs {

    override val errorCodeIfs: ErrorCodeIfs?
    override val errorDescription: String?

    constructor(errorCodeIfs: ErrorCodeIfs) : super(errorCodeIfs.getDescription()) {
        this.errorCodeIfs = errorCodeIfs
        this.errorDescription = errorCodeIfs.getDescription()
    }

    constructor(
        errorCodeIfs: ErrorCodeIfs,
        errorCodeDescription: String
    ): super(errorCodeDescription) {
        this.errorCodeIfs = errorCodeIfs
        this.errorDescription = errorCodeDescription
    }

    constructor(
        errorCodeIfs: ErrorCodeIfs,
        throwable: Throwable
    ): super(throwable) {
        this.errorCodeIfs = errorCodeIfs
        this.errorDescription = errorCodeIfs.getDescription()
    }

    constructor(
        errorCodeIfs: ErrorCodeIfs,
        throwable: Throwable,
        errorDescription: String
    ): super(throwable) {
        this.errorCodeIfs = errorCodeIfs
        this.errorDescription = errorDescription
    }


}