package com.jun.blogducinemaback.global.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T:Any> T.logger(): Logger = LoggerFactory.getLogger(this::class.java)

