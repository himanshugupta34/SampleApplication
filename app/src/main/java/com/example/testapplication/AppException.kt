package com.example.testapplication

import java.io.IOException

class ApiException(message: String) : IOException(message)
class NoNetworkException(message: String) : IOException(message)