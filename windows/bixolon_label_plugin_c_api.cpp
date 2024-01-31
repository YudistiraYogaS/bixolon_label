#include "include/bixolon_label/bixolon_label_plugin_c_api.h"

#include <flutter/plugin_registrar_windows.h>

#include "bixolon_label_plugin.h"

void BixolonLabelPluginCApiRegisterWithRegistrar(
    FlutterDesktopPluginRegistrarRef registrar) {
  bixolon_label::BixolonLabelPlugin::RegisterWithRegistrar(
      flutter::PluginRegistrarManager::GetInstance()
          ->GetRegistrar<flutter::PluginRegistrarWindows>(registrar));
}
