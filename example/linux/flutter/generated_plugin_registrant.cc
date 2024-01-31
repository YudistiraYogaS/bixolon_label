//
//  Generated file. Do not edit.
//

// clang-format off

#include "generated_plugin_registrant.h"

#include <bixolon_label/bixolon_label_plugin.h>

void fl_register_plugins(FlPluginRegistry* registry) {
  g_autoptr(FlPluginRegistrar) bixolon_label_registrar =
      fl_plugin_registry_get_registrar_for_plugin(registry, "BixolonLabelPlugin");
  bixolon_label_plugin_register_with_registrar(bixolon_label_registrar);
}
