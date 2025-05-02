package com.imagepalette

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.ReadableType

class ImagePaletteModule internal constructor(context: ReactApplicationContext) :
  ImagePaletteSpec(context) {

  private val context = context
  private val imgPalette = ImagePalette()

  override fun getName(): String {
    return NAME
  }

  companion object {
    const val NAME = "ImagePalette"
  }

  private fun getHeadersFromConfig(config: ReadableMap?): MutableMap<String, String> {
    val headers = mutableMapOf<String, String>()
    config?.getMap("headers")?.let { configHeaders ->
      val iterator = configHeaders.keySetIterator()
      while (iterator.hasNextKey()) {
        val key = iterator.nextKey()
        when (configHeaders.getType(key)) {
          ReadableType.String -> headers[key] = configHeaders.getString(key) ?: ""
          else -> throw IllegalArgumentException("Unsupported type")
        }
      }
    }
    return headers
  }

  private fun getPixelSpacingFromConfig(config: ReadableMap?): Int {
    return config?.getInt("pixelSpacingAndroid") ?: 5
  }

  private fun parseSegments(segments: ReadableArray): ArrayList<ImagePalette.ImageSegmentConfig> {
    val segmentsParsed: ArrayList<ImagePalette.ImageSegmentConfig> = ArrayList()

    for (i in 0 until segments.size()) {
      val segmentMap = segments.getMap(i)
      segmentMap?.let {
        segmentsParsed.add(
          ImagePalette.ImageSegmentConfig(
            fromY = it.getInt("fromY"),
            toY = it.getInt("toY"),
            fromX = it.getInt("fromX"),
            toX = it.getInt("toX"),
          )
        )
      }
    }

    return segmentsParsed
  }


  private fun getFallbackColorFromConfig(config: ReadableMap?): String {
    return config?.getString("fallbackColor") ?: "#fff"
  }

  @ReactMethod
  override fun getPalette(uri: String, config: ReadableMap?, promise: Promise) {
    val headers = this.getHeadersFromConfig(config)
    val fallback = this.getFallbackColorFromConfig(config)
    imgPalette.getPalette(uri, context, fallback, headers, promise)
  }

  @ReactMethod
  override fun getAverageColor(uri: String, config: ReadableMap?, promise: Promise) {
    val headers = getHeadersFromConfig(config)
    val pixelSpacing = this.getPixelSpacingFromConfig(config)
    imgPalette.getAverageColor(uri, context, headers, pixelSpacing, promise)
  }


  @ReactMethod
  override fun getSegmentsAverageColor(
    uri: String,
    segments: ReadableArray,
    config: ReadableMap?,
    promise: Promise
  ) {
    val headers = this.getHeadersFromConfig(config)
    val segmentsParsed = this.parseSegments(segments)
    val pixelSpacing = this.getPixelSpacingFromConfig(config)
    imgPalette.getSegmentsAverageColor(
      uri,
      context,
      pixelSpacing,
      headers,
      segmentsParsed,
      promise
    )
  }

  @ReactMethod
  override fun getSegmentsPalette(
    uri: String,
    segments: ReadableArray,
    config: ReadableMap?,
    promise: Promise
  ) {
    val headers = this.getHeadersFromConfig(config)
    val segmentsParsed = this.parseSegments(segments)
    val fallback = this.getFallbackColorFromConfig(config)
    imgPalette.getSegmentsPalette(
      uri,
      context,
      fallback,
      headers,
      segmentsParsed,
      promise
    )
  }
}
