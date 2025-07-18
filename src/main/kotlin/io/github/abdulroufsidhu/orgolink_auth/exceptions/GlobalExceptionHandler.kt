package io.github.abdulroufsidhu.orgolink_auth.exceptions

import io.github.abdulroufsidhu.orgolink_auth.dto.ValidResponseData
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
  @ExceptionHandler(DataIntegrityViolationException::class)
  fun dataIntegrityVoilationException(ex: DataIntegrityViolationException) =
          ResponseEntity.badRequest()
                  .body(ValidResponseData(message = ex.rootCause?.message, data = ex.stackTrace))

  @ExceptionHandler(UsernameAlreadyExists::class)
  fun usernameAlreadyExists(ex: UsernameAlreadyExists) =
          ResponseEntity.badRequest().body(ValidResponseData(message = ex.message, ex.cause))

  @ExceptionHandler(MethodArgumentNotValidException::class)
  fun validationException(ex: MethodArgumentNotValidException) =
          ResponseEntity.badRequest()
                  .body(
                          ValidResponseData(
                                  message = "validation error",
                                  data =
                                          ex.bindingResult.allErrors.map { e ->
                                            HashMap<String, String>().apply {
                                              put(
                                                      (e as FieldError).field,
                                                      e.defaultMessage ?: "Invalid input"
                                              )
                                            }
                                          }
                          )
                  )

  @ExceptionHandler(ProjectNotFoundException::class)
  fun projectNotFoundException(ex: ProjectNotFoundException) =
          ResponseEntity.status(HttpStatus.NOT_FOUND)
                  .body(ValidResponseData(message = ex.message, data = null))

  @ExceptionHandler(InsufficientPermissionException::class)
  fun insufficientPermissionException(ex: InsufficientPermissionException) =
          ResponseEntity.status(HttpStatus.FORBIDDEN)
                  .body(ValidResponseData(message = ex.message, data = null))

  @ExceptionHandler(IllegalArgumentException::class)
  fun illegalArgumentException(ex: IllegalArgumentException) =
          ResponseEntity.badRequest().body(ValidResponseData(message = ex.message, data = null))

  @ExceptionHandler(RuntimeException::class)
  fun runtimeException(ex: RuntimeException) =
          ResponseEntity.internalServerError()
                  .body(
                          ValidResponseData(
                                  message = ex.message ?: "Internal server error",
                                  data = null
                          )
                  )
}
