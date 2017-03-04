package com.clara

import com.twitter.finagle.Service

package object velocify {
  type VelocifyClient = Service[VelocifyPayload, Unit]
}
