/**
 * Context and problem
 * Most applications rely on other systems for some data or functionality.
 * For example, when a legacy application is migrated to a modern system,
 * it may still need existing legacy resources. New features must be able to call the legacy system.
 * This is especially true of gradual migrations,
 * where different features of a larger application are moved to a modern system over time.
 *
 * <p>Often these legacy systems suffer from quality issues such as convoluted data schemas
 * or obsolete APIs.
 * The features and technologies used in legacy systems can vary widely from more modern systems.
 * To interoperate with the legacy system,
 * the new application may need to support outdated infrastructure, protocols, data models, APIs,
 * or other features that you wouldn't otherwise put into a modern application.
 *
 * <p>Maintaining access between new and legacy systems can force the new system to adhere to
 * at least some of the legacy system's APIs or other semantics.
 * When these legacy features have quality issues, supporting them "corrupts" what might
 * otherwise be a cleanly designed modern application.
 * Similar issues can arise with any external system that your development team doesn't control,
 * not just legacy systems.
 *
 * <p>Solution Isolate the different subsystems by placing an anti-corruption layer between them.
 * This layer translates communications between the two systems,
 * allowing one system to remain unchanged while the other can avoid compromising
 * its design and technological approach.
 */
package com.iluwatar.corruption;
