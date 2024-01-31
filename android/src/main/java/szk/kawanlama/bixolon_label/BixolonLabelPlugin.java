package szk.kawanlama.bixolon_label;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

import com.bixolon.commonlib.BXLCommonConst;
import com.bixolon.commonlib.common.BXLFileHelper;
import com.bixolon.commonlib.log.LogService;
import com.bixolon.labelprinter.BixolonLabelPrinter;

/** BixolonLabelPlugin */
public class BixolonLabelPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private BixolonLabelPrinter mBixolonLabelPrinter;
  private Context context;

  static {
    try {
      System.loadLibrary("bxl_common");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "bixolon_label");
    channel.setMethodCallHandler(this);
    this.context = flutterPluginBinding.getApplicationContext();
    this.mBixolonLabelPrinter = new BixolonLabelPrinter(this.context);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    switch (call.method) {
      case "connectIp":
        final String ipAddress = call.argument("ipAddress");
        this.mBixolonLabelPrinter.connect(ipAddress, 9100, 5000);
        if (this.mBixolonLabelPrinter.isConnected()) {
          result.success(true);
        } else {
          result.success(false);
        }
        break;
      case "printText":
        try {
          final String data = call.argument("text");
          this.mBixolonLabelPrinter.beginTransactionPrint();
          this.mBixolonLabelPrinter.drawText(data, 50, 50, 51, 1, 1, 0, 0, false, false, 48);
          this.mBixolonLabelPrinter.print(1, 1);
          this.mBixolonLabelPrinter.endTransactionPrint();
          result.success("success print data");
        } catch (Exception e) {
          result.success(e.getMessage());
        }
        break;
      case "printImage":
        final byte[] byteData = call.argument("byteData");
        final int horizontalStartPosition = call.argument("horizontalPosition");
        final int verticalStartPosition = call.argument("verticalPosition");
        final int width = call.argument("width");
        final int level = call.argument("level");
        final boolean dithering = call.argument("dithering");

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        Bitmap bmp = BitmapFactory.decodeByteArray(byteData, 0, byteData.length, options);
        this.mBixolonLabelPrinter.beginTransactionPrint();
        this.mBixolonLabelPrinter.drawBitmap(bmp, horizontalStartPosition, verticalStartPosition, width, level, dithering);
        this.mBixolonLabelPrinter.print(1,1);
        this.mBixolonLabelPrinter.endTransactionPrint();
        result.success("success print image");
        break;
      case "disconnect":
        try {
          this.mBixolonLabelPrinter.disconnect();
          result.success(true);
        } catch (Exception e) {
          result.success(false);
        }
        break;
      case "isConnected":
        result.success(this.mBixolonLabelPrinter.isConnected());
        break;
      default:
        result.notImplemented();
        break;
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}
