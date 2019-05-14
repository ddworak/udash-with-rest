package io.company.app.backend.rpc.secure

import io.company.app.backend.rpc.secure.chat.ChatEndpoint
import io.company.app.backend.services.DomainServices
import io.company.app.shared.model.auth.UserContext
import io.company.app.shared.rpc.server.secure.SecureRPC
import io.company.app.shared.rpc.server.secure.chat.ChatRPC

class SecureEndpoint(implicit domainServices: DomainServices, ctx: UserContext) extends SecureRPC {
  import domainServices._

  lazy val chatEndpoint = new ChatEndpoint

  override def chat(): ChatRPC = chatEndpoint
}
