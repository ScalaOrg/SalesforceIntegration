package com.clara.velocify

object VelocifyClientConfig {
  val Host     = "secure.velocify.com"
  val Provider = "ExpediteFinancial"
  val Client   = "30451"
}

case class VelocifyClientConfig(
  host:     String = VelocifyClientConfig.Host,
  provider: String = VelocifyClientConfig.Provider,
  client:   String = VelocifyClientConfig.Client
) {
  def url = s"https://$host/Import.aspx?Provider=$provider&Client=$client"
}
