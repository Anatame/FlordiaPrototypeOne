package com.anatame.flordia.utils.server

class Server(val serverAddress: ServerAddress) {
    fun run(){
      val serverClient = ServerManager.BuildServer()
          .createServer(serverAddress.host, serverAddress.port)
          .build()

        serverClient.startServer()
    }
}

data class ServerAddress(
    val host: String,
    val port: Int,
)