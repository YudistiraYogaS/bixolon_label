import 'package:flutter_test/flutter_test.dart';
import 'package:bixolon_label/bixolon_label.dart';
import 'package:bixolon_label/bixolon_label_platform_interface.dart';
import 'package:bixolon_label/bixolon_label_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockBixolonLabelPlatform
    with MockPlatformInterfaceMixin
    implements BixolonLabelPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final BixolonLabelPlatform initialPlatform = BixolonLabelPlatform.instance;

  test('$MethodChannelBixolonLabel is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelBixolonLabel>());
  });

  test('getPlatformVersion', () async {
    BixolonLabel bixolonLabelPlugin = BixolonLabel();
    MockBixolonLabelPlatform fakePlatform = MockBixolonLabelPlatform();
    BixolonLabelPlatform.instance = fakePlatform;

    expect(await bixolonLabelPlugin.getPlatformVersion(), '42');
  });
}
