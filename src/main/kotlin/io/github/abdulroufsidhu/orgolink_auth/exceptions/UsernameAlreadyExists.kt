package io.github.abdulroufsidhu.orgolink_auth.exceptions

class UsernameAlreadyExists : RuntimeException {
    constructor(exception: Throwable) : super(exception)
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(p0: String, p1: Throwable, p2: Boolean, p3: Boolean) : super(p0, p1, p2, p3)
}