package com.oleksii.surovtsev.portfolio.util

/**
 * Base interface for resource loaders.
 */
interface ResourceLoader {
    /**
     * Loads a resource as a string from the specified path.
     *
     * @param path Resource path relative to the loader's base directory
     * @return Resource content as string
     * @throws IllegalArgumentException if resource is not found
     */
    fun loadResource(path: String): String
}
