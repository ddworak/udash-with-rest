package io.company.app.frontend

import io.company.app.frontend.routing.{LoginPageState, RoutingRegistryDef, RoutingState, StatesToViewFactoryDef}
import io.company.app.frontend.services.rpc.{NotificationsCenter, RPCService}
import io.company.app.frontend.services.{TranslationsService, UserContextService}
import io.company.app.shared.model.SharedExceptions
import io.company.app.shared.rpc.client.MainClientRPC
import io.company.app.shared.rpc.server.MainServerRPC
import io.udash._
import io.udash.rpc._

object ApplicationContext {
  import scala.concurrent.ExecutionContext.Implicits.global

  private val routingRegistry = new RoutingRegistryDef
  private val viewFactoryRegistry = new StatesToViewFactoryDef

  val application = new Application[RoutingState](
    routingRegistry, viewFactoryRegistry, WindowUrlPathChangeProvider
  )

  application.onRoutingFailure {
    case _: SharedExceptions.UnauthorizedException =>
      // automatic redirection to LoginPage
      application.goTo(LoginPageState)
  }

  val notificationsCenter: NotificationsCenter = new NotificationsCenter

  // creates RPC connection to the server
  val serverRpc: MainServerRPC = DefaultServerRPC[MainClientRPC, MainServerRPC](
    new RPCService(notificationsCenter), exceptionsRegistry = new SharedExceptions
  )

  val translationsService: TranslationsService = new TranslationsService(serverRpc.translations())
  val userService: UserContextService = new UserContextService(serverRpc)
}