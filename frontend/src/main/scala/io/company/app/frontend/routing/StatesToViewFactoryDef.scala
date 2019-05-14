package io.company.app.frontend.routing

import io.company.app.frontend.ApplicationContext
import io.company.app.frontend.views.RootViewFactory
import io.company.app.frontend.views.chat.ChatViewFactory
import io.company.app.frontend.views.login.LoginPageViewFactory
import io.udash._

class StatesToViewFactoryDef extends ViewFactoryRegistry[RoutingState] {
  def matchStateToResolver(state: RoutingState): ViewFactory[_ <: RoutingState] =
    state match {
      case RootState => new RootViewFactory(
        ApplicationContext.translationsService
      )
      case LoginPageState => new LoginPageViewFactory(
        ApplicationContext.userService, ApplicationContext.application, ApplicationContext.translationsService
      )
      case ChatState => new ChatViewFactory(
        ApplicationContext.userService, ApplicationContext.translationsService, ApplicationContext.notificationsCenter
      )
    }
}