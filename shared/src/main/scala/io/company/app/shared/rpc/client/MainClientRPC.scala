package io.company.app.shared.rpc.client

import io.company.app.shared.rpc.client.chat.ChatNotificationsRPC
import io.udash.rpc._

trait MainClientRPC {
  def chat(): ChatNotificationsRPC
}

object MainClientRPC extends DefaultClientRpcCompanion[MainClientRPC]