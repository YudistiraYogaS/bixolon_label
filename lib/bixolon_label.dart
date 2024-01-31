
import 'bixolon_label_platform_interface.dart';

class BixolonLabel {

   Future<String?> printImage(Map<String, dynamic> param) {
     try {
       return BixolonLabelPlatform.instance.printImage(param);
     } catch (e) {
       rethrow;
     }
  }

  Future<String?> printText(Map<String,dynamic> param) {
    try {
     return BixolonLabelPlatform.instance.printText(param);
    } catch (e) {
      rethrow;
    }
  }

  Future<bool?> connectIp(Map<String, dynamic> param) {
     try {
       return BixolonLabelPlatform.instance.connectIp(param);
     } catch (e) {
       rethrow;
     }
  }

  Future<bool?> disconnect() {
     try {
       return BixolonLabelPlatform.instance.disconnect();
     } catch (e) {
       rethrow;
     }
  }

  Future<bool?> isConnected() {
     try {
       return BixolonLabelPlatform.instance.isConnected();
     } catch (e) {
       rethrow;
     }
  }
}
