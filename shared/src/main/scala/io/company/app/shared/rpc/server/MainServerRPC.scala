package io.company.app.shared.rpc.server

import io.company.app.shared.model.auth.UserToken
import io.company.app.shared.rpc.server.open.AuthRPC
import io.company.app.shared.rpc.server.secure.SecureRPC
import io.udash.i18n._
import io.udash.rpc._

trait MainServerRPC {
  /** Returns an RPC for authentication. */
  def auth(): AuthRPC

  /** Verifies provided UserToken and returns a [[io.company.app.shared.rpc.server.secure.SecureRPC]] if the token is valid. */
  def secure(token: UserToken): SecureRPC

  /** Returns an RPC serving translations from the server resources. */
  def translations(): RemoteTranslationRPC
}

object MainServerRPC extends DefaultServerRpcCompanion[MainServerRPC]