package com.bixolon.labelprinter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.bixolon.commonlib.connectivity.BluetoothLEService;
import com.bixolon.commonlib.connectivity.BluetoothService;
import com.bixolon.commonlib.connectivity.ConnectivityManager;
import com.bixolon.commonlib.connectivity.NetworkService;
import com.bixolon.commonlib.connectivity.USBService;
import com.bixolon.commonlib.deviceinfo.XDeviceInfo;
import com.bixolon.commonlib.deviceinfo.XDeviceList;
import com.bixolon.commonlib.emul.SLCSEmul;
import com.bixolon.commonlib.log.LogService;
import java.nio.ByteBuffer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: PrinterControl.kt */
@Metadata(mv = {1, 5, 1}, k = 1, xi = 48, d1 = {"��b\n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\b\n��\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010\u0012\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\t\u0018�� =2\u00020\u0001:\u0001=B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\u0015\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u0011¢\u0006\u0002\u0010\u001bJ\u0006\u0010\u001c\u001a\u00020\u000fJ\u0006\u0010\u001d\u001a\u00020\rJ\u0006\u0010\u001e\u001a\u00020\u000fJ\u0006\u0010\u001f\u001a\u00020\u000fJ\u0006\u0010 \u001a\u00020\u000fJ\u0006\u0010!\u001a\u00020\u0011J\u0010\u0010\"\u001a\u00020\u00112\u0006\u0010#\u001a\u00020$H\u0002J\u0010\u0010%\u001a\u00020\u00112\u0006\u0010#\u001a\u00020$H\u0002J\u0010\u0010&\u001a\u00020\u00112\u0006\u0010#\u001a\u00020$H\u0002J\u0010\u0010'\u001a\u00020\u00112\u0006\u0010(\u001a\u00020\u0015H\u0002J\u0010\u0010)\u001a\u00020\u00112\u0006\u0010#\u001a\u00020$H\u0002J\u0010\u0010*\u001a\u00020\u00112\u0006\u0010+\u001a\u00020,H\u0002J(\u0010-\u001a\u00020\u00152\u0006\u0010.\u001a\u00020\u000f2\b\u0010/\u001a\u0004\u0018\u00010\u00152\u0006\u00100\u001a\u00020\u000f2\u0006\u00101\u001a\u00020\u000fJ\u0006\u00102\u001a\u00020\u0019J\u001a\u00103\u001a\u00020\u00192\u0012\u00104\u001a\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020\u001905J\u000e\u00103\u001a\u00020$2\u0006\u00106\u001a\u00020\u0011J\u000e\u00103\u001a\u00020$2\u0006\u00101\u001a\u00020\u000fJ\u000e\u00107\u001a\u00020\u00192\u0006\u00108\u001a\u00020\u000fJ\u0018\u00109\u001a\u00020\u00192\u0006\u0010(\u001a\u00020\u00152\u0006\u0010:\u001a\u00020\u0011H\u0002J\u000e\u0010;\u001a\u00020\u00112\u0006\u0010+\u001a\u00020$J\u001e\u0010;\u001a\u00020$2\u0006\u0010+\u001a\u00020$2\u0006\u0010<\u001a\u00020\u00112\u0006\u00106\u001a\u00020\u0011R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n��R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0012\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0013\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0016\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0017\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��¨\u0006>"}, d2 = {"Lcom/bixolon/labelprinter/PrinterControl;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "uiHandler", "Landroid/os/Handler;", "looper", "Landroid/os/Looper;", "(Landroid/content/Context;Landroid/os/Handler;Landroid/os/Looper;)V", "connectivityManager", "Lcom/bixolon/commonlib/connectivity/ConnectivityManager;", "deviceInfo", "Lcom/bixolon/commonlib/deviceinfo/XDeviceInfo;", "mCurrentProcess", "", "mIsMobile", "", "mPrinterDpi", "mPrinterInch", "mPrinterModelName", "", "maxRFIDPosition", "minRFIDPosition", "disableInactivityTime", "", "disable", "(Z)Lkotlin/Unit;", "getConnectedPrinterDpi", "getDeviceInfo", "getMaxRFIDPosition", "getMinRFIDPosition", "getPrinterInch", "isConnected", "isOneByteStatusResponse", "data", "", "isPDFResponse", "isResponseEnd", "isSupportedPrinterModel", "modelName", "isTwoByteStatusResponse", "isValidReceiveData", "buffer", "Ljava/nio/ByteBuffer;", "printerConnect", "type", "address", "port", "timeout", "printerDisconnect", "read", "readListener", "Lkotlin/Function1;", "sendHandler", "setCurrentProcess", "process", "setPrinterModel", "isMobile", "write", "receiveData", "Companion", "BixolonLabelSDK_release"})
/* loaded from: BixolonLabelPrinterLibrary_V2.0.4.jar:com/bixolon/labelprinter/PrinterControl.class */
public final class PrinterControl {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Context context;
    @NotNull
    private Handler uiHandler;
    @Nullable
    private Looper looper;
    @NotNull
    private XDeviceInfo deviceInfo;
    private boolean mIsMobile;
    @NotNull
    private String mPrinterModelName;
    private int mPrinterDpi;
    private int mPrinterInch;
    private int mCurrentProcess;
    private int minRFIDPosition;
    private int maxRFIDPosition;
    @Nullable
    private ConnectivityManager connectivityManager;
    @NotNull
    private static final String TAG = "PrinterControl";
    @NotNull
    private static final String VENDOR_NAME = "BIXOLON";
    private static final int GET_PRINTER_MODEL_NAME = 0;

    public PrinterControl(@NotNull Context context, @NotNull Handler uiHandler, @Nullable Looper looper) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(uiHandler, "uiHandler");
        this.context = context;
        this.uiHandler = uiHandler;
        this.looper = looper;
        this.deviceInfo = new XDeviceInfo();
        this.mPrinterModelName = "";
        this.mPrinterDpi = 2;
        this.mPrinterInch = 4;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PrinterControl(@NotNull Context context) {
        this(context, new Handler(), null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* compiled from: PrinterControl.kt */
    @Metadata(mv = {1, 5, 1}, k = 1, xi = 48, d1 = {"��\u001a\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\b\n��\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n��¨\u0006\b"}, d2 = {"Lcom/bixolon/labelprinter/PrinterControl$Companion;", "", "()V", "GET_PRINTER_MODEL_NAME", "", "TAG", "", "VENDOR_NAME", "BixolonLabelSDK_release"})
    /* loaded from: BixolonLabelPrinterLibrary_V2.0.4.jar:com/bixolon/labelprinter/PrinterControl$Companion.class */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }
    }

    @NotNull
    public final String printerConnect(int type, @Nullable String address, int port, int timeout) {
        String str;
        LogService.LogI(2, TAG, "++ printerConnect(" + type + ',' + ((Object) address) + ',' + port + ',' + timeout + ") ++");
        if (this.connectivityManager != null) {
            ConnectivityManager connectivityManager = this.connectivityManager;
            Integer valueOf = connectivityManager == null ? null : Integer.valueOf(connectivityManager.getConnectionState());
            if (valueOf != null && valueOf.intValue() == 2) {
                LogService.LogE(2, TAG, "++ ALREADY CONNECTED ++ ");
                BixolonLabelPrinter.Companion.setLAST_ERROR(1);
                return "";
            }
        }
        this.uiHandler.obtainMessage(BixolonLabelPrinter.MESSAGE_STATE_CHANGE, 1, 0).sendToTarget();
        switch (type) {
            case 0:
                this.connectivityManager = new BluetoothService(this.context);
                break;
            case 1:
                this.connectivityManager = new NetworkService(this.context);
                break;
            case 2:
                this.connectivityManager = new USBService(this.context);
                break;
            case 3:
                this.connectivityManager = new BluetoothLEService(this.context);
                break;
            default:
                BixolonLabelPrinter.Companion.setLAST_ERROR(BixolonLabelPrinter.RC_DEVICE_CONNECTION_FAIL);
                return "";
        }
        ConnectivityManager connectivityManager2 = this.connectivityManager;
        Integer valueOf2 = connectivityManager2 == null ? null : Integer.valueOf(connectivityManager2.connect(address, port, timeout, false));
        if (valueOf2 != null && valueOf2.intValue() == 0) {
            SLCSEmul command = SLCSEmul.getInstance();
            command.AddGetInformation(0);
            byte[] PopAll = command.PopAll();
            Intrinsics.checkNotNullExpressionValue(PopAll, "command.PopAll()");
            String it = new String(write(PopAll, true, true), Charsets.UTF_8);
            if (it.length() > 2) {
                str = it.substring(0, it.length() - 2);
                Intrinsics.checkNotNullExpressionValue(str, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            } else {
                str = "";
            }
            String readModelName = str;
            LogService.LogD(2, TAG, "++ modelName : " + readModelName + " ++ ");
            if (!isSupportedPrinterModel(readModelName)) {
                LogService.LogE(2, TAG, "++ not supported model ++ ");
                printerDisconnect();
                BixolonLabelPrinter.Companion.setLAST_ERROR(1007);
                return "";
            }
            this.deviceInfo.GetDeviceInfo(readModelName, 0);
            Bundle bundle = new Bundle();
            bundle.putString(BixolonLabelPrinter.DEVICE_NAME, readModelName);
            Message msg = this.uiHandler.obtainMessage(106);
            Intrinsics.checkNotNullExpressionValue(msg, "uiHandler.obtainMessage(BixolonLabelPrinter.MESSAGE_DEVICE_NAME)");
            msg.setData(bundle);
            this.uiHandler.sendMessage(msg);
            this.uiHandler.obtainMessage(BixolonLabelPrinter.MESSAGE_STATE_CHANGE, 2, this.deviceInfo.GetInch(), readModelName).sendToTarget();
            BixolonLabelPrinter.Companion.setLAST_ERROR(0);
            return readModelName;
        }
        this.uiHandler.obtainMessage(BixolonLabelPrinter.MESSAGE_STATE_CHANGE, 0, -1).sendToTarget();
        BixolonLabelPrinter.Companion.setLAST_ERROR(BixolonLabelPrinter.RC_DEVICE_CONNECTION_FAIL);
        return "";
    }

    public final void printerDisconnect() {
        LogService.LogI(2, TAG, "++ printerDisconnect() ++ ");
        if (this.connectivityManager == null) {
            ConnectivityManager connectivityManager = this.connectivityManager;
            Integer valueOf = connectivityManager == null ? null : Integer.valueOf(connectivityManager.getConnectionState());
            if (valueOf == null || valueOf.intValue() != 2) {
                return;
            }
        }
        ConnectivityManager connectivityManager2 = this.connectivityManager;
        if (connectivityManager2 != null) {
            connectivityManager2.disconnect();
        }
        this.connectivityManager = null;
        this.uiHandler.obtainMessage(BixolonLabelPrinter.MESSAGE_STATE_CHANGE, 0, -1).sendToTarget();
    }

    private final boolean isSupportedPrinterModel(String modelName) {
        LogService.LogI(2, TAG, "++ isSupportedPrinterModel : " + modelName + " ++ ");
        if (modelName.length() == 0) {
            return false;
        }
        Object[] supportedPrinterNameList = new XDeviceList().GetLabelMobilePrinterList(VENDOR_NAME);
        Intrinsics.checkNotNullExpressionValue(supportedPrinterNameList, "supportedPrinterNameList");
        Object[] $this$forEach$iv = supportedPrinterNameList;
        for (Object element$iv : $this$forEach$iv) {
            String it = (String) element$iv;
            Intrinsics.checkNotNullExpressionValue(it, "it");
            if (StringsKt.contains$default(it, modelName, false, 2, (Object) null)) {
                setPrinterModel(modelName, true);
                return true;
            }
        }
        Object[] supportedPrinterNameList2 = new XDeviceList().GetLabelPrinterList(VENDOR_NAME);
        Intrinsics.checkNotNullExpressionValue(supportedPrinterNameList2, "supportedPrinterNameList");
        Object[] $this$forEach$iv2 = supportedPrinterNameList2;
        for (Object element$iv2 : $this$forEach$iv2) {
            String it2 = (String) element$iv2;
            Intrinsics.checkNotNullExpressionValue(it2, "it");
            if (StringsKt.contains$default(it2, modelName, false, 2, (Object) null)) {
                setPrinterModel(modelName, false);
                return true;
            }
        }
        return false;
    }

    private final void setPrinterModel(String modelName, boolean isMobile) {
        this.mPrinterModelName = modelName;
        this.mIsMobile = isMobile;
    }

    @NotNull
    public final byte[] write(@NotNull byte[] buffer, boolean receiveData, boolean sendHandler) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        LogService.LogI(2, TAG, "++ write ++");
        if (this.connectivityManager == null) {
            LogService.LogE(2, TAG, "++ printer is not connected ++");
            BixolonLabelPrinter.Companion.setLAST_ERROR(BixolonLabelPrinter.RC_DEVICE_NOT_CONNECTED);
            return new byte[0];
        }
        ConnectivityManager connectivityManager = this.connectivityManager;
        Integer valueOf = connectivityManager == null ? null : Integer.valueOf(connectivityManager.getConnectionState());
        if (valueOf == null || valueOf.intValue() != 2) {
            LogService.LogE(2, TAG, "++ printer is not connected ++");
            BixolonLabelPrinter.Companion.setLAST_ERROR(BixolonLabelPrinter.RC_DEVICE_NOT_CONNECTED);
            return new byte[0];
        }
        ConnectivityManager connectivityManager2 = this.connectivityManager;
        if (connectivityManager2 != null) {
            connectivityManager2.write(buffer);
        }
        return receiveData ? read(sendHandler) : new byte[0];
    }

    public final boolean write(@NotNull byte[] buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        LogService.LogI(2, TAG, "++ write ++");
        if (this.connectivityManager == null) {
            LogService.LogE(2, TAG, "++ printer is not connected ++");
            BixolonLabelPrinter.Companion.setLAST_ERROR(BixolonLabelPrinter.RC_DEVICE_NOT_CONNECTED);
            return false;
        }
        ConnectivityManager connectivityManager = this.connectivityManager;
        Integer valueOf = connectivityManager == null ? null : Integer.valueOf(connectivityManager.getConnectionState());
        if (valueOf == null || valueOf.intValue() != 2) {
            LogService.LogE(2, TAG, "++ printer is not connected ++");
            BixolonLabelPrinter.Companion.setLAST_ERROR(BixolonLabelPrinter.RC_DEVICE_NOT_CONNECTED);
            return false;
        }
        ConnectivityManager connectivityManager2 = this.connectivityManager;
        if (connectivityManager2 == null) {
            return true;
        }
        connectivityManager2.write(buffer);
        return true;
    }

    @NotNull
    public final byte[] read(boolean sendHandler) {
        LogService.LogI(2, TAG, "++ read ++");
        ByteBuffer buf = ByteBuffer.allocate(1024);
        long startTime = System.currentTimeMillis();
        byte[] data = new byte[0];
        do {
            try {
                ConnectivityManager connectivityManager = this.connectivityManager;
                byte[] received = connectivityManager == null ? null : connectivityManager.read(500);
                if (received != null) {
                    buf.put(received);
                }
                Intrinsics.checkNotNullExpressionValue(buf, "buf");
                if (isValidReceiveData(buf)) {
                    data = new byte[buf.position()];
                    System.arraycopy(buf.array(), 0, data, 0, buf.position());
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (System.currentTimeMillis() - startTime < 3000);
        if (sendHandler) {
            this.uiHandler.obtainMessage(100, this.mCurrentProcess, 0, data).sendToTarget();
        }
        setCurrentProcess(0);
        return data;
    }

    @NotNull
    public final byte[] read(int timeout) {
        long endTime;
        LogService.LogI(2, TAG, "++ read(" + timeout + ") ++");
        ByteBuffer buf = ByteBuffer.allocate(1024);
        long startTime = System.currentTimeMillis();
        do {
            try {
                ConnectivityManager connectivityManager = this.connectivityManager;
                byte[] received = connectivityManager == null ? null : connectivityManager.read(500);
                if (received != null) {
                    buf.put(received);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            endTime = System.currentTimeMillis();
        } while (endTime - startTime < timeout);
        byte[] data = new byte[buf.position()];
        System.arraycopy(buf.array(), 0, data, 0, buf.position());
        return data;
    }

    public final void read(@NotNull Function1<? super byte[], Unit> function1) {
        long endTime;
        Intrinsics.checkNotNullParameter(function1, "readListener");
        LogService.LogI(2, TAG, "++ read ++");
        ByteBuffer buf = ByteBuffer.allocate(1024);
        long startTime = System.currentTimeMillis();
        byte[] data = new byte[0];
        do {
            try {
                ConnectivityManager connectivityManager = this.connectivityManager;
                byte[] received = connectivityManager == null ? null : connectivityManager.read(500);
                if (received != null) {
                    buf.put(received);
                }
                Intrinsics.checkNotNullExpressionValue(buf, "buf");
                if (isValidReceiveData(buf)) {
                    data = new byte[buf.position()];
                    System.arraycopy(buf.array(), 0, data, 0, buf.position());
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            endTime = System.currentTimeMillis();
        } while (endTime - startTime < 3000);
        function1.invoke(data);
    }

    public final void setCurrentProcess(int process) {
        this.mCurrentProcess = process;
    }

    private final boolean isValidReceiveData(ByteBuffer buffer) {
        LogService.LogI(2, TAG, "++ isValidReceiveData ++ ");
        if (buffer.position() == 0) {
            return false;
        }
        byte[] data = new byte[buffer.position()];
        System.arraycopy(buffer.array(), 0, data, 0, buffer.position());
        switch (data.length) {
            case 1:
                return isPDFResponse(data) || isOneByteStatusResponse(data);
            case 2:
                return isTwoByteStatusResponse(data);
            default:
                return isResponseEnd(data);
        }
    }

    private final boolean isPDFResponse(byte[] data) {
        LogService.LogI(2, BixolonLabelPrinter.TAG, "++ isPDFResponse() ++");
        return data[0] == 48 || data[0] == 49;
    }

    private final boolean isOneByteStatusResponse(byte[] data) {
        if (data[0] == 0 || data[0] == 1 || data[0] == 32 || data[0] == 64 || data[0] == Byte.MIN_VALUE) {
            return true;
        }
        return false;
    }

    private final boolean isTwoByteStatusResponse(byte[] data) {
        if (data[0] == 0 && (data[1] == Byte.MIN_VALUE || data[1] == 64 || data[1] == 32 || data[1] == 1 || data[1] == 0)) {
            return true;
        }
        if (data[1] == 0) {
            return data[0] == Byte.MIN_VALUE || data[0] == 64 || data[0] == 32 || data[0] == 8 || data[0] == 4 || data[0] == 0;
        }
        return false;
    }

    private final boolean isResponseEnd(byte[] data) {
        if (data[data.length - 2] == 13 && data[data.length - 1] == 10) {
            return true;
        }
        return false;
    }

    public final int getConnectedPrinterDpi() {
        switch (this.deviceInfo.GetDPI()) {
            case 203:
                return 2;
            case 300:
                return 3;
            case 600:
                return 6;
            default:
                return 2;
        }
    }

    public final int getPrinterInch() {
        return this.deviceInfo.GetInch();
    }

    public final int getMinRFIDPosition() {
        return this.deviceInfo.GetRFIDTransPosMin();
    }

    public final int getMaxRFIDPosition() {
        return this.deviceInfo.GetRFIDTransPosMax();
    }

    public final boolean isConnected() {
        ConnectivityManager connectivityManager = this.connectivityManager;
        Integer valueOf = connectivityManager == null ? null : Integer.valueOf(connectivityManager.getConnectionState());
        return valueOf != null && valueOf.intValue() == 2;
    }

    @NotNull
    public final XDeviceInfo getDeviceInfo() {
        return this.deviceInfo;
    }

    @Nullable
    public final Unit disableInactivityTime(boolean disable) {
        ConnectivityManager connectivityManager = this.connectivityManager;
        if (connectivityManager == null) {
            return null;
        }
        connectivityManager.disableInactivityTime(disable);
        return Unit.INSTANCE;
    }
}