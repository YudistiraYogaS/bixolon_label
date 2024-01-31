#ifndef FLUTTER_PLUGIN_BIXOLON_LABEL_PLUGIN_H_
#define FLUTTER_PLUGIN_BIXOLON_LABEL_PLUGIN_H_

#include <flutter/method_channel.h>
#include <flutter/plugin_registrar_windows.h>

#include <memory>

namespace bixolon_label {

class BixolonLabelPlugin : public flutter::Plugin {
 public:
  static void RegisterWithRegistrar(flutter::PluginRegistrarWindows *registrar);

  BixolonLabelPlugin();

  virtual ~BixolonLabelPlugin();

  // Disallow copy and assign.
  BixolonLabelPlugin(const BixolonLabelPlugin&) = delete;
  BixolonLabelPlugin& operator=(const BixolonLabelPlugin&) = delete;

  // Called when a method is called on this plugin's channel from Dart.
  void HandleMethodCall(
      const flutter::MethodCall<flutter::EncodableValue> &method_call,
      std::unique_ptr<flutter::MethodResult<flutter::EncodableValue>> result);
};

}  // namespace bixolon_label

#endif  // FLUTTER_PLUGIN_BIXOLON_LABEL_PLUGIN_H_
