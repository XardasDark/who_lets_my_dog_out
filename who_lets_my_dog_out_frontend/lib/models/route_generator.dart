/* import 'package:flutter/material.dart';
import '../screens/chatPage.dart';
//import '../screens/homePage.dart';
import '../screens/profilPage.dart';

class RouteGenerator {
  static Route<dynamic> generateRoute(RouteSettings settings) {
    // Getting arguments passed in while calling Navigator.pushNamed
    final args = settings.arguments;

    switch (settings.name) {
      case '/ChatPage':
        return MaterialPageRoute(builder: (_) => const ChatPage());
      case '/ProfilePage':
        // Validation of correct data type
        if (args is List<String>) {
          return MaterialPageRoute(
            builder: (_) => ProfilePage(
              listPlayerNames: args,
            ),
          );
        }
        // If args is not of the correct type, return an error page.
        // You can also throw an exception while in development.
        return _errorRoute();
      default:
        // If there is no such named route in the switch statement, e.g. /third
        return _errorRoute();
    }
  }

  static Route<dynamic> _errorRoute() {
    return MaterialPageRoute(builder: (_) {
      return Scaffold(
        appBar: AppBar(
          title: const Text('Error'),
        ),
        body: const Center(
          child: Text('ERROR'),
        ),
      );
    });
  }
}
 */