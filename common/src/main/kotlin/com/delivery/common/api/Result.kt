package com.delivery.common.api

import com.delivery.common.error.ErrorCode
import com.delivery.common.error.ErrorCodeIfs

data class Result (
    val resultCode: Int?=null,
    val resultMessage: String?=null,
    val resultDescription: String?=null
){

    companion object { // static

        fun OK(): Result {
            return Result(
                resultCode = ErrorCode.OK.getErrorCode(),
                resultMessage = ErrorCode.OK.getDescription(),
                resultDescription = "성공"
            )
        }

        fun ERROR(errorCodeIfs: ErrorCodeIfs): Result {
            return Result(
                resultCode = errorCodeIfs.getErrorCode(),
                resultMessage = errorCodeIfs.getDescription(),
                resultDescription = "실패"
            )
        }

        fun ERROR(
            errorCodeIfs: ErrorCodeIfs,
            tx: Throwable
            ): Result {
            return Result(
                resultCode = errorCodeIfs.getErrorCode(),
                resultMessage = errorCodeIfs.getDescription(),
                resultDescription = tx.localizedMessage
            )
        }

        fun ERROR(
            errorCodeIfs: ErrorCodeIfs,
            description: String
        ): Result {
            return Result(
                resultCode = errorCodeIfs.getErrorCode(),
                resultMessage = errorCodeIfs.getDescription(),
                resultDescription = description
            )
        }

    }
}