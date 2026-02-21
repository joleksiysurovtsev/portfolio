package com.oleksii.surovtsev.portfolio.service

import com.oleksii.surovtsev.portfolio.util.JsonResourceLoader
import com.oleksii.surovtsev.portfolio.util.SvgResourceLoader
import com.oleksii.surovtsev.portfolio.util.TextResourceLoader
import org.springframework.stereotype.Service

/**
 * Service that provides centralized access to resource loaders.
 * Replaces the deprecated UtilFileManager.
 */
@Service
class ResourceLoaderService(
    val json: JsonResourceLoader,
    val text: TextResourceLoader,
    val svg: SvgResourceLoader
)