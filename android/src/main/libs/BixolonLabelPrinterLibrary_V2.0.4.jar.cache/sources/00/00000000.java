package com.bixolon.labelprinter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import com.bixolon.commonlib.common.BXLFileHelper;
import com.bixolon.commonlib.common.BXLHelper;
import com.bixolon.commonlib.connectivity.searcher.BXLBluetooth;
import com.bixolon.commonlib.connectivity.searcher.BXLNetwork;
import com.bixolon.commonlib.connectivity.searcher.BXLUsbDevice;
import com.bixolon.commonlib.emul.SLCSEmul;
import com.bixolon.commonlib.emul.image.LabelImage;
import com.bixolon.commonlib.log.LogService;
import com.bixolon.commonlib.queue.BXLQueue;
import com.bixolon.pdflib.License;
import com.bixolon.pdflib.PdfCore;
import com.bixolon.pdflib.PdfDocument;
import com.bixolon.pdflib.util.Size;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BixolonLabelPrinter.kt */
@Metadata(mv = {1, 5, 1}, k = 1, xi = 48, d1 = {"��®\u0001\n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000b\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\b\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n��\n\u0002\u0018\u0002\n\u0002\b(\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\f\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b)\n\u0002\u0010\u0012\n��\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b<\n\u0002\u0010\u0005\n\u0002\b\u001f\u0018�� \u0082\u00022\u00020\u0001:\u0004\u0082\u0002\u0083\u0002B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\u0006\u0010\u001f\u001a\u00020 J\u0006\u0010!\u001a\u00020\u0015J\u000e\u0010\"\u001a\u00020 2\u0006\u0010#\u001a\u00020\u0006J\u0006\u0010$\u001a\u00020\u0015J\u0006\u0010%\u001a\u00020&J\u000e\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(J\u0016\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020&J\u000e\u0010%\u001a\u00020&2\u0006\u0010*\u001a\u00020&J\u0016\u0010%\u001a\u00020&2\u0006\u0010*\u001a\u00020&2\u0006\u0010+\u001a\u00020\u0015J\u001e\u0010%\u001a\u00020&2\u0006\u0010*\u001a\u00020&2\u0006\u0010,\u001a\u00020\u00152\u0006\u0010-\u001a\u00020\u0015J(\u0010%\u001a\u00020&2\b\u0010*\u001a\u0004\u0018\u00010&2\u0006\u0010+\u001a\u00020\u00152\u0006\u0010,\u001a\u00020\u00152\u0006\u0010-\u001a\u00020\u0015J\u0010\u0010.\u001a\u00020\u00152\u0006\u0010/\u001a\u00020\u0015H\u0002J\u000e\u00100\u001a\u00020 2\u0006\u00101\u001a\u00020\rJ\u0006\u00102\u001a\u00020 JV\u00103\u001a\u00020\u00152\u0006\u00104\u001a\u00020&2\u0006\u00105\u001a\u00020\u00152\u0006\u00106\u001a\u00020\u00152\u0006\u00107\u001a\u00020\u00152\u0006\u00108\u001a\u00020\u00152\u0006\u00109\u001a\u00020\u00152\u0006\u0010:\u001a\u00020\u00152\u0006\u0010;\u001a\u00020\u00152\u0006\u0010<\u001a\u00020\u00152\u0006\u0010=\u001a\u00020\u0015JX\u0010>\u001a\u00020\u00152\u0006\u00104\u001a\u00020&2\u0006\u00105\u001a\u00020\u00152\u0006\u00106\u001a\u00020\u00152\u0006\u0010?\u001a\u00020\u00152\u0006\u0010@\u001a\u00020\r2\u0006\u0010A\u001a\u00020\u00152\u0006\u0010B\u001a\u00020\r2\u0006\u0010C\u001a\u00020\u00152\b\u0010D\u001a\u0004\u0018\u00010&2\u0006\u0010;\u001a\u00020\u0015JN\u0010E\u001a\u00020\u00152\u0006\u0010F\u001a\u00020&2\u0006\u0010G\u001a\u00020\r2\u0006\u0010H\u001a\u00020\u00152\u0006\u0010I\u001a\u00020\u00152\u0006\u0010J\u001a\u00020\u00152\u0006\u0010K\u001a\u00020\u00152\u0006\u0010L\u001a\u00020\u00152\u0006\u0010M\u001a\u00020\u00152\u0006\u0010;\u001a\u00020\u0015JV\u0010E\u001a\u00020\u00152\u0006\u0010F\u001a\u00020&2\u0006\u0010G\u001a\u00020\r2\u0006\u0010H\u001a\u00020\u00152\u0006\u0010I\u001a\u00020\u00152\u0006\u0010J\u001a\u00020\u00152\u0006\u0010K\u001a\u00020\u00152\u0006\u0010L\u001a\u00020\u00152\u0006\u0010M\u001a\u00020\u00152\u0006\u0010;\u001a\u00020\u00152\u0006\u0010N\u001a\u00020\u0015J6\u0010O\u001a\u00020\u00152\u0006\u0010P\u001a\u00020Q2\u0006\u0010R\u001a\u00020\u00152\u0006\u0010S\u001a\u00020\u00152\u0006\u0010J\u001a\u00020\u00152\u0006\u0010T\u001a\u00020\u00152\u0006\u0010U\u001a\u00020\rJ>\u0010O\u001a\u00020\u00152\u0006\u0010P\u001a\u00020Q2\u0006\u0010R\u001a\u00020\u00152\u0006\u0010S\u001a\u00020\u00152\u0006\u0010J\u001a\u00020\u00152\u0006\u0010T\u001a\u00020\u00152\u0006\u0010U\u001a\u00020\r2\u0006\u0010N\u001a\u00020\u0015JF\u0010O\u001a\u00020\u00152\u0006\u0010P\u001a\u00020Q2\u0006\u0010R\u001a\u00020\u00152\u0006\u0010S\u001a\u00020\u00152\u0006\u0010J\u001a\u00020\u00152\u0006\u0010T\u001a\u00020\u00152\u0006\u0010U\u001a\u00020\r2\u0006\u0010;\u001a\u00020\u00152\u0006\u0010N\u001a\u00020\u0015J6\u0010O\u001a\u00020\u00152\u0006\u0010V\u001a\u00020&2\u0006\u0010R\u001a\u00020\u00152\u0006\u0010S\u001a\u00020\u00152\u0006\u0010J\u001a\u00020\u00152\u0006\u0010T\u001a\u00020\u00152\u0006\u0010U\u001a\u00020\rJ>\u0010O\u001a\u00020\u00152\u0006\u0010V\u001a\u00020&2\u0006\u0010R\u001a\u00020\u00152\u0006\u0010S\u001a\u00020\u00152\u0006\u0010J\u001a\u00020\u00152\u0006\u0010T\u001a\u00020\u00152\u0006\u0010U\u001a\u00020\r2\u0006\u0010N\u001a\u00020\u0015J6\u0010W\u001a\u00020\u00152\u0006\u0010R\u001a\u00020\u00152\u0006\u0010S\u001a\u00020\u00152\u0006\u0010X\u001a\u00020\u00152\u0006\u0010Y\u001a\u00020\u00152\u0006\u0010Z\u001a\u00020\u00152\u0006\u0010[\u001a\u00020\u0015J&\u0010\\\u001a\u00020\u00152\u0006\u0010R\u001a\u00020\u00152\u0006\u0010S\u001a\u00020\u00152\u0006\u0010?\u001a\u00020\u00152\u0006\u0010]\u001a\u00020\u0015JV\u0010^\u001a\u00020\u00152\u0006\u00104\u001a\u00020&2\u0006\u00105\u001a\u00020\u00152\u0006\u00106\u001a\u00020\u00152\u0006\u0010_\u001a\u00020\u00152\u0006\u0010`\u001a\u00020\u00152\u0006\u0010:\u001a\u00020\u00152\u0006\u0010a\u001a\u00020\r2\u0006\u0010b\u001a\u00020\u00152\u0006\u0010c\u001a\u00020d2\u0006\u0010e\u001a\u00020\u0015JN\u0010f\u001a\u00020\u00152\u0006\u00104\u001a\u00020&2\u0006\u00105\u001a\u00020\u00152\u0006\u00106\u001a\u00020\u00152\u0006\u0010_\u001a\u00020\u00152\u0006\u0010`\u001a\u00020\u00152\u0006\u0010:\u001a\u00020\u00152\u0006\u0010<\u001a\u00020\u00152\u0006\u0010g\u001a\u00020\u00152\u0006\u0010;\u001a\u00020\u0015J6\u0010h\u001a\u00020\u00152\u0006\u0010P\u001a\u00020Q2\u0006\u0010R\u001a\u00020\u00152\u0006\u0010S\u001a\u00020\u00152\u0006\u0010J\u001a\u00020\u00152\u0006\u0010T\u001a\u00020\u00152\u0006\u0010U\u001a\u00020\rJ>\u0010h\u001a\u00020\u00152\u0006\u0010P\u001a\u00020Q2\u0006\u0010R\u001a\u00020\u00152\u0006\u0010S\u001a\u00020\u00152\u0006\u0010J\u001a\u00020\u00152\u0006\u0010T\u001a\u00020\u00152\u0006\u0010U\u001a\u00020\r2\u0006\u0010N\u001a\u00020\u0015JF\u0010h\u001a\u00020\u00152\u0006\u0010P\u001a\u00020Q2\u0006\u0010R\u001a\u00020\u00152\u0006\u0010S\u001a\u00020\u00152\u0006\u0010J\u001a\u00020\u00152\u0006\u0010T\u001a\u00020\u00152\u0006\u0010U\u001a\u00020\r2\u0006\u0010;\u001a\u00020\u00152\u0006\u0010N\u001a\u00020\u0015J6\u0010h\u001a\u00020\u00152\u0006\u0010V\u001a\u00020&2\u0006\u0010R\u001a\u00020\u00152\u0006\u0010S\u001a\u00020\u00152\u0006\u0010J\u001a\u00020\u00152\u0006\u0010T\u001a\u00020\u00152\u0006\u0010U\u001a\u00020\rJ>\u0010h\u001a\u00020\u00152\u0006\u0010V\u001a\u00020&2\u0006\u0010R\u001a\u00020\u00152\u0006\u0010S\u001a\u00020\u00152\u0006\u0010J\u001a\u00020\u00152\u0006\u0010T\u001a\u00020\u00152\u0006\u0010U\u001a\u00020\r2\u0006\u0010N\u001a\u00020\u0015J.\u0010i\u001a\u00020\u00152\u0006\u00104\u001a\u00020&2\u0006\u00105\u001a\u00020\u00152\u0006\u00106\u001a\u00020\u00152\u0006\u0010?\u001a\u00020\u00152\u0006\u0010;\u001a\u00020\u0015J.\u0010j\u001a\u00020\u00152\u0006\u00104\u001a\u00020&2\u0006\u00105\u001a\u00020\u00152\u0006\u00106\u001a\u00020\u00152\u0006\u0010<\u001a\u00020\r2\u0006\u0010;\u001a\u00020\u0015J>\u0010k\u001a\u00020\u00152\u0006\u0010P\u001a\u00020Q2\u0006\u0010H\u001a\u00020\u00152\u0006\u0010I\u001a\u00020\u00152\u0006\u0010J\u001a\u00020\u00152\u0006\u0010K\u001a\u00020\u00152\u0006\u0010L\u001a\u00020\u00152\u0006\u0010M\u001a\u00020\u0015JF\u0010k\u001a\u00020\u00152\u0006\u0010P\u001a\u00020Q2\u0006\u0010H\u001a\u00020\u00152\u0006\u0010I\u001a\u00020\u00152\u0006\u0010J\u001a\u00020\u00152\u0006\u0010K\u001a\u00020\u00152\u0006\u0010L\u001a\u00020\u00152\u0006\u0010M\u001a\u00020\u00152\u0006\u0010;\u001a\u00020\u0015J>\u0010l\u001a\u00020\u00152\u0006\u0010m\u001a\u00020&2\u0006\u0010H\u001a\u00020\u00152\u0006\u0010I\u001a\u00020\u00152\u0006\u0010J\u001a\u00020\u00152\u0006\u0010K\u001a\u00020\u00152\u0006\u0010L\u001a\u00020\u00152\u0006\u0010M\u001a\u00020\u0015JF\u0010l\u001a\u00020\u00152\u0006\u0010m\u001a\u00020&2\u0006\u0010H\u001a\u00020\u00152\u0006\u0010I\u001a\u00020\u00152\u0006\u0010J\u001a\u00020\u00152\u0006\u0010K\u001a\u00020\u00152\u0006\u0010L\u001a\u00020\u00152\u0006\u0010M\u001a\u00020\u00152\u0006\u0010;\u001a\u00020\u0015JV\u0010n\u001a\u00020\u00152\u0006\u00104\u001a\u00020&2\u0006\u00105\u001a\u00020\u00152\u0006\u00106\u001a\u00020\u00152\u0006\u0010_\u001a\u00020\u00152\u0006\u0010`\u001a\u00020\u00152\u0006\u0010:\u001a\u00020\u00152\u0006\u0010o\u001a\u00020\u00152\u0006\u0010p\u001a\u00020\r2\u0006\u0010<\u001a\u00020\u00152\u0006\u0010;\u001a\u00020\u0015J&\u0010q\u001a\u00020\u00152\u0006\u00104\u001a\u00020&2\u0006\u00105\u001a\u00020\u00152\u0006\u00106\u001a\u00020\u00152\u0006\u0010c\u001a\u00020\u0015J>\u0010r\u001a\u00020\u00152\u0006\u00104\u001a\u00020&2\u0006\u00105\u001a\u00020\u00152\u0006\u00106\u001a\u00020\u00152\u0006\u0010s\u001a\u00020\u00152\u0006\u0010:\u001a\u00020\u00152\u0006\u0010c\u001a\u00020\u00152\u0006\u0010;\u001a\u00020\u0015JF\u0010t\u001a\u00020\u00152\u0006\u0010u\u001a\u00020v2\u0006\u0010R\u001a\u00020\u00152\u0006\u0010S\u001a\u00020\u00152\u0006\u0010w\u001a\u00020\u00152\u0006\u0010J\u001a\u00020\u00152\u0006\u0010T\u001a\u00020\u00152\u0006\u0010U\u001a\u00020\r2\u0006\u0010x\u001a\u00020\rJV\u0010t\u001a\u00020\u00152\u0006\u0010u\u001a\u00020v2\u0006\u0010R\u001a\u00020\u00152\u0006\u0010S\u001a\u00020\u00152\u0006\u0010w\u001a\u00020\u00152\u0006\u0010J\u001a\u00020\u00152\u0006\u0010T\u001a\u00020\u00152\u0006\u0010U\u001a\u00020\r2\u0006\u0010x\u001a\u00020\r2\u0006\u0010;\u001a\u00020\u00152\u0006\u0010N\u001a\u00020\u0015Jf\u0010y\u001a\u00020\u00152\u0006\u00104\u001a\u00020&2\u0006\u00105\u001a\u00020\u00152\u0006\u00106\u001a\u00020\u00152\u0006\u0010z\u001a\u00020\u00152\u0006\u0010{\u001a\u00020\u00152\u0006\u0010|\u001a\u00020\u00152\u0006\u0010}\u001a\u00020\u00152\u0006\u0010<\u001a\u00020\u00152\u0006\u0010~\u001a\u00020\u00152\u0006\u0010J\u001a\u00020\u00152\u0006\u0010:\u001a\u00020\u00152\u0006\u0010;\u001a\u00020\u0015JN\u0010\u007f\u001a\u00020\u00152\u0006\u00104\u001a\u00020&2\u0006\u00105\u001a\u00020\u00152\u0006\u00106\u001a\u00020\u00152\u0006\u0010_\u001a\u00020\u00152\u0006\u0010`\u001a\u00020\u00152\u0006\u0010:\u001a\u00020\u00152\u0006\u0010p\u001a\u00020\r2\u0006\u0010<\u001a\u00020\u00152\u0006\u0010;\u001a\u00020\u0015J@\u0010\u0080\u0001\u001a\u00020\u00152\u0006\u00104\u001a\u00020&2\u0006\u00105\u001a\u00020\u00152\u0006\u00106\u001a\u00020\u00152\u0007\u0010\u0081\u0001\u001a\u00020\u00152\u0006\u0010A\u001a\u00020\u00152\u0006\u0010?\u001a\u00020\u00152\u0006\u0010;\u001a\u00020\u0015JT\u0010\u0082\u0001\u001a\u00020\u00152\u0006\u00104\u001a\u00020&2\u0006\u00105\u001a\u00020\u00152\u0006\u00106\u001a\u00020\u00152\u0007\u0010\u0083\u0001\u001a\u00020\u00152\u0007\u0010\u0084\u0001\u001a\u00020\u00152\u0007\u0010\u0085\u0001\u001a\u00020\u00152\u0007\u0010\u0086\u0001\u001a\u00020\u00152\u0007\u0010\u0087\u0001\u001a\u00020\u00152\u0006\u0010;\u001a\u00020\u0015JQ\u0010\u0088\u0001\u001a\u00020\u00152\u0006\u00104\u001a\u00020&2\u0006\u00105\u001a\u00020\u00152\u0006\u00106\u001a\u00020\u00152\u0006\u0010_\u001a\u00020\u00152\u0006\u0010`\u001a\u00020\u00152\u0006\u0010:\u001a\u00020\u00152\u0007\u0010\u0089\u0001\u001a\u00020\u00152\u0007\u0010\u008a\u0001\u001a\u00020\u00152\u0006\u0010;\u001a\u00020\u0015Jf\u0010\u008b\u0001\u001a\u00020\u00152\u0006\u00104\u001a\u00020&2\u0006\u00105\u001a\u00020\u00152\u0006\u00106\u001a\u00020\u00152\u0007\u0010\u008c\u0001\u001a\u00020\u00152\u0007\u0010\u008d\u0001\u001a\u00020\u00152\u0007\u0010\u008e\u0001\u001a\u00020\u00152\u0007\u0010\u008f\u0001\u001a\u00020\u00152\u0006\u0010;\u001a\u00020\u00152\u0007\u0010\u0090\u0001\u001a\u00020\r2\u0007\u0010\u0091\u0001\u001a\u00020\r2\u0007\u0010\u0092\u0001\u001a\u00020\u0015J\\\u0010\u0093\u0001\u001a\u00020\u00152\u0006\u0010R\u001a\u00020\u00152\u0006\u0010S\u001a\u00020\u00152\u0006\u0010X\u001a\u00020\u00152\u0006\u0010Y\u001a\u00020\u00152\u0006\u0010Z\u001a\u00020\u00152\u0007\u0010\u0094\u0001\u001a\u00020\u00152\u0007\u0010\u0095\u0001\u001a\u00020\u00152\u0007\u0010\u0096\u0001\u001a\u00020\u00152\u0007\u0010\u0097\u0001\u001a\u00020\u00152\u0007\u0010\u0098\u0001\u001a\u00020\u0015Jv\u0010\u0099\u0001\u001a\u00020\u00152\u0006\u00104\u001a\u00020&2\u0006\u00105\u001a\u00020\u00152\u0006\u00106\u001a\u00020\u00152\u0007\u0010\u009a\u0001\u001a\u00020\u00152\u0006\u0010J\u001a\u00020\u00152\u0006\u0010:\u001a\u00020\u00152\u0007\u0010\u008f\u0001\u001a\u00020\u00152\u0007\u0010\u0091\u0001\u001a\u00020\r2\u0007\u0010\u0090\u0001\u001a\u00020\r2\u0007\u0010\u009b\u0001\u001a\u00020\r2\u0006\u0010;\u001a\u00020\u00152\u0007\u0010\u0092\u0001\u001a\u00020\u00152\u0007\u0010\u009c\u0001\u001a\u00020\u0015J\u0007\u0010\u009d\u0001\u001a\u00020\u0015J(\u0010\u009e\u0001\u001a\u00020 2\b\u0010\u009f\u0001\u001a\u00030 \u00012\u0015\u0010¡\u0001\u001a\u0010\u0012\u0005\u0012\u00030 \u0001\u0012\u0004\u0012\u00020 0¢\u0001J#\u0010\u009e\u0001\u001a\u00020 2\b\u0010\u009f\u0001\u001a\u00030 \u00012\u0007\u0010£\u0001\u001a\u00020\r2\u0007\u0010¤\u0001\u001a\u00020\u0015J'\u0010\u009e\u0001\u001a\u00020 2\u0007\u0010\u009f\u0001\u001a\u00020&2\u0015\u0010¡\u0001\u001a\u0010\u0012\u0005\u0012\u00030 \u0001\u0012\u0004\u0012\u00020 0¢\u0001J\"\u0010\u009e\u0001\u001a\u00020 2\u0007\u0010\u009f\u0001\u001a\u00020&2\u0007\u0010£\u0001\u001a\u00020\r2\u0007\u0010¤\u0001\u001a\u00020\u0015J\u0011\u0010¥\u0001\u001a\u00020 2\b\u0010\u009f\u0001\u001a\u00030 \u0001J\u000f\u0010¦\u0001\u001a\n\u0012\u0005\u0012\u00030¨\u00010§\u0001J\u000f\u0010©\u0001\u001a\u00020&2\u0006\u0010-\u001a\u00020\u0015J\u0007\u0010ª\u0001\u001a\u00020&J\t\u0010«\u0001\u001a\u00020\u0015H\u0002J\u000f\u0010¬\u0001\u001a\u00020\u00152\u0006\u0010u\u001a\u00020vJ\u0007\u0010\u00ad\u0001\u001a\u00020\u0015J\t\u0010®\u0001\u001a\u00020\rH\u0002J\u0012\u0010¯\u0001\u001a\u00020\u00152\u0007\u0010°\u0001\u001a\u00020&H\u0002J\u001a\u0010±\u0001\u001a\u0004\u0018\u00010Q2\u0006\u0010u\u001a\u00020v2\u0007\u0010²\u0001\u001a\u00020\u0015J\u0018\u0010³\u0001\u001a\u00020\u00152\u0006\u0010u\u001a\u00020v2\u0007\u0010´\u0001\u001a\u00020\u0015J\u0007\u0010µ\u0001\u001a\u00020\u0015J\u0011\u0010¶\u0001\u001a\u00030 \u00012\u0007\u0010·\u0001\u001a\u00020\u0015J'\u0010¶\u0001\u001a\u00020 2\u0007\u0010·\u0001\u001a\u00020\u00152\u0015\u0010¡\u0001\u001a\u0010\u0012\u0005\u0012\u00030 \u0001\u0012\u0004\u0012\u00020 0¢\u0001J\u0011\u0010¸\u0001\u001a\u00030 \u00012\u0007\u0010¹\u0001\u001a\u00020\rJ'\u0010¸\u0001\u001a\u00020 2\u0007\u0010¹\u0001\u001a\u00020\r2\u0015\u0010¡\u0001\u001a\u0010\u0012\u0005\u0012\u00030 \u0001\u0012\u0004\u0012\u00020 0¢\u0001J\u0007\u0010º\u0001\u001a\u00020\u0015J\u0013\u0010»\u0001\u001a\u00020\u00152\b\u0010\u009f\u0001\u001a\u00030 \u0001H\u0002J\u0007\u0010¼\u0001\u001a\u00020\rJ\u0013\u0010½\u0001\u001a\u00020\r2\b\u0010¾\u0001\u001a\u00030 \u0001H\u0002J\u0007\u0010¿\u0001\u001a\u00020\u0015J\u0019\u0010À\u0001\u001a\u00020\u00152\u0007\u0010Á\u0001\u001a\u00020\u00152\u0007\u0010Â\u0001\u001a\u00020\u0015J\u0007\u0010Ã\u0001\u001a\u00020\u0015J\u0019\u0010Ä\u0001\u001a\u00020\u00152\u0007\u0010Å\u0001\u001a\u00020\r2\u0007\u0010Æ\u0001\u001a\u00020\u0015J\u0019\u0010Ç\u0001\u001a\u00020\u00152\u0007\u0010Å\u0001\u001a\u00020\r2\u0007\u0010È\u0001\u001a\u00020\u0015J\u0010\u0010É\u0001\u001a\u00020\u00152\u0007\u0010Ê\u0001\u001a\u00020\rJ\u0018\u0010Ë\u0001\u001a\u00020\u00152\u0007\u0010Ì\u0001\u001a\u00020\u00152\u0006\u0010/\u001a\u00020\u0015J\u000f\u0010Í\u0001\u001a\u00020 2\u0006\u0010/\u001a\u00020\u0015J\u0010\u0010Î\u0001\u001a\u00020\u00152\u0007\u0010Ï\u0001\u001a\u00020\u0015J\u0010\u0010Ð\u0001\u001a\u00020\u00152\u0007\u0010Ñ\u0001\u001a\u00020\u0015J\u0019\u0010Ò\u0001\u001a\u00020\u00152\u0007\u0010Ó\u0001\u001a\u00020\u00152\u0007\u0010Ô\u0001\u001a\u00020&J\u0010\u0010Õ\u0001\u001a\u00020\u00152\u0007\u0010Ö\u0001\u001a\u00020\u0015J+\u0010×\u0001\u001a\u00020\u00152\u0007\u0010Ø\u0001\u001a\u00020\u00152\u0007\u0010Ù\u0001\u001a\u00020\u00152\u0007\u0010Ú\u0001\u001a\u00020\u00152\u0007\u0010Û\u0001\u001a\u00020\u0015J\u0019\u0010Ü\u0001\u001a\u00020\u00152\u0007\u0010Ý\u0001\u001a\u00020\u00152\u0007\u0010Þ\u0001\u001a\u00020\u0015J\u0010\u0010ß\u0001\u001a\u00020\u00152\u0007\u0010à\u0001\u001a\u00020\u0015J\u0010\u0010á\u0001\u001a\u00020\u00152\u0007\u0010â\u0001\u001a\u00020\u0015J\u0013\u0010ã\u0001\u001a\u00020\r2\b\u0010ä\u0001\u001a\u00030å\u0001H\u0002J\u0010\u0010æ\u0001\u001a\u00020\u00152\u0007\u0010°\u0001\u001a\u00020&J\u0010\u0010ç\u0001\u001a\u00020 2\u0007\u0010è\u0001\u001a\u00020\u0015J\u000f\u0010é\u0001\u001a\u00020\u00152\u0006\u0010+\u001a\u00020\u0015J+\u0010ê\u0001\u001a\u00020\u00152\u0007\u0010ë\u0001\u001a\u00020&2\u0007\u0010ì\u0001\u001a\u00020&2\u0007\u0010í\u0001\u001a\u00020&2\u0007\u0010î\u0001\u001a\u00020&J\u0010\u0010ï\u0001\u001a\u00020\u00152\u0007\u0010ð\u0001\u001a\u00020\u0015J\u0010\u0010ñ\u0001\u001a\u00020\u00152\u0007\u0010Å\u0001\u001a\u00020\rJ\u0010\u0010ò\u0001\u001a\u00020\u00152\u0007\u0010ó\u0001\u001a\u00020\u0015J\u000f\u0010ô\u0001\u001a\u00020 2\u0006\u0010\u001d\u001a\u00020\u001eJ\u0010\u0010õ\u0001\u001a\u00020\u00152\u0007\u0010ö\u0001\u001a\u00020\u0015J+\u0010÷\u0001\u001a\u00020\u00152\u0007\u0010ø\u0001\u001a\u00020\u00152\u0007\u0010ù\u0001\u001a\u00020\u00152\u0007\u0010ú\u0001\u001a\u00020\u00152\u0007\u0010û\u0001\u001a\u00020\u0015J\u0010\u0010ü\u0001\u001a\u00020\u00152\u0007\u0010ý\u0001\u001a\u00020&J%\u0010ü\u0001\u001a\u00020\u00152\u0007\u0010ý\u0001\u001a\u00020&2\u0013\u0010\u001d\u001a\u000f\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020 0¢\u0001J*\u0010þ\u0001\u001a\u00020\u00152\u0007\u0010ÿ\u0001\u001a\u00020\u00152\u0007\u0010\u0080\u0002\u001a\u00020\u00152\u0007\u0010\u0081\u0002\u001a\u00020\u00152\u0006\u00104\u001a\u00020&R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n��R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n��R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.¢\u0006\u0002\n��R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n��R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082.¢\u0006\u0002\n��R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n��R\u0016\u0010\u001a\u001a\n \u001c*\u0004\u0018\u00010\u001b0\u001bX\u0082\u0004¢\u0006\u0002\n��R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��¨\u0006\u0084\u0002"}, d2 = {"Lcom/bixolon/labelprinter/BixolonLabelPrinter;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "uiHandler", "Landroid/os/Handler;", "looper", "Landroid/os/Looper;", "(Landroid/content/Context;Landroid/os/Handler;Landroid/os/Looper;)V", "bxlQueue", "Lcom/bixolon/commonlib/queue/BXLQueue;", "isPDFAvailable", "", "pageSizeDPI", "Lcom/bixolon/pdflib/util/Size;", "pdfCore", "Lcom/bixolon/pdflib/PdfCore;", "pdfDoc", "Lcom/bixolon/pdflib/PdfDocument;", "pdfDpi", "", "pdfLicense", "Lcom/bixolon/pdflib/License;", "printerControl", "Lcom/bixolon/labelprinter/PrinterControl;", "slcsEmul", "Lcom/bixolon/commonlib/emul/SLCSEmul;", "kotlin.jvm.PlatformType", "transferFileListener", "Lcom/bixolon/labelprinter/BixolonLabelPrinter$TransferFileListener;", "beginTransactionPrint", "", "calibrateRFID", "changeHandler", "handler", "clearBuffer", "connect", "", "device", "Landroid/hardware/usb/UsbDevice;", "deviceName", "address", "type", "port", "timeout", "convertInternalCodePage", "codePage", "disableInactivityTime", "disable", "disconnect", "draw1dBarcode", "data", "horizontalPosition", "verticalPosition", "barcodeSelection", "narrowBarWidth", "wideBarWidth", "height", "rotation", "hri", "quietZoneWidth", "drawAztec", "size", "extendedChannel", "eccLevel", "menuSymbol", "numberOfSymbols", "optionalID", "drawBase64Image", "base64Img", "isTransparent", "startPosX", "startPosY", "width", "threshold", "ditheringType", "compressType", "brightness", "drawBitmap", "bitmap", "Landroid/graphics/Bitmap;", "horizontalStartPosition", "verticalStartPosition", "level", "dithering", "pathName", "drawBlock", "horizontalEndPosition", "verticalEndPosition", "option", "thickness", "drawCircle", "multiplier", "drawCodaBlock", "widthNarrow", "widthWide", "securityLevel", "dataColumns", "mode", "", "encode", "drawCode49", "startingMode", "drawCompressionImage", "drawDataMatrix", "drawIMBBarcode", "drawImage", "drawImageFile", "fileName", "drawMSIBarcode", "checkDigit", "printCheckDigit", "drawMaxicode", "drawMicroPDF417", "moduleWidth", "drawPDFFile", "uri", "Landroid/net/Uri;", "page", "compress", "drawPdf417", "maxRow", "maxColumn", "errorCorrection", "compression", "originPoint", "drawPlesseyBarcode", "drawQrCode", "model", "drawRSSBarcode", "barcodeType", "magnification", "seperator", "BarHeight", "SegmentWidth", "drawTLC39Barcode", "rowHeightOfMicroPDF417", "narrowWidthOfMicroPDF417", "drawText", "fontSize", "horizontalMultiplier", "verticalMultiplier", "rightSpace", "reverse", "bold", "alignment", "drawTowBlock", "horizontalStartPositionSquare2", "verticalStartPositionSquare2", "horizontalEndPositionSquare2", "verticalEndPositionSquare2", "optionSquare2", "drawVectorFontText", "font", "italic", "direction", "endTransactionPrint", "executeDirectIo", "command", "", "readListener", "Lkotlin/Function1;", "hasResponse", "responseLength", "executeDirectIoWithoutCRLF", "findBluetoothPrinters", "", "Landroid/bluetooth/BluetoothDevice;", "findNetworkPrinters", "findUsbPrinters", "getCodePage", "getCountPdfPages", "getLastError", "getPDFAvailable", "getPDFLicense", "key", "getPdfPage", "pageNum", "getPdfPageHeight", "index", "getPrinterDpi", "getPrinterInformation", "param", "getStatus", "checkImageBuffer", "initializePrinter", "inputCommand", "isConnected", "isValidGetPDFLicenseResponse", "buffer", "lockRFID", "print", "set", "copy", "printInformation", "setAutoCutter", "enabled", "cuttingPeriod", "setBackFeedOption", "quantity", "setBufferMode", "doubleBuffering", "setCharacterSet", "internationalCharacterSet", "setCodePage", "setCutterPosition", "position", "setDensity", "density", "setEPCDataStructure", "totalSize", "fieldSizes", "setLeftMarginPosition", "shiftVal", "setLength", "labelLength", "gapLength", "mediaType", "offsetLength", "setMargin", "horizontalMargin", "verticalMargin", "setOffset", "offset", "setOrientation", "orientation", "setPDFAvailable", "value", "", "setPDFLicenseKey", "setPdfDpi", "dpi", "setPrintingType", "setRFIDPassword", "oldAccessPwd", "oldKillPwd", "newAccessPwd", "newKillPwd", "setRFIDPosition", "transPosition", "setRewinder", "setSpeed", "speed", "setTransferFileListener", "setWidth", "labelWidth", "setupRFID", "rfidType", "numberOfRetries", "numberOfLabel", "radioPower", "transferFile", "filePath", "writeRFID", "dataType", "startingBlockNumber", "dataLength", "Companion", "TransferFileListener", "BixolonLabelSDK_release"})
/* loaded from: BixolonLabelPrinterLibrary_V2.0.4.jar:com/bixolon/labelprinter/BixolonLabelPrinter.class */
public final class BixolonLabelPrinter {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Context context;
    @NotNull
    private Handler uiHandler;
    @Nullable
    private final Looper looper;
    @NotNull
    private PrinterControl printerControl;
    private PdfDocument pdfDoc;
    private PdfCore pdfCore;
    private License pdfLicense;
    private Size pageSizeDPI;
    private int pdfDpi;
    private boolean isPDFAvailable;
    private final SLCSEmul slcsEmul;
    @NotNull
    private final BXLQueue bxlQueue;
    @Nullable
    private TransferFileListener transferFileListener;
    @NotNull
    public static final String TAG = "BixolonLabelPrinter";
    @NotNull
    public static final String VERSION = "2.0.4";
    public static final int STATE_NONE = 0;
    public static final int STATE_CONNECTING = 1;
    public static final int STATE_CONNECTED = 2;
    public static final int INTERFACE_TYPE_BLUETOOTH = 0;
    public static final int INTERFACE_TYPE_NETWORK = 1;
    public static final int INTERFACE_TYPE_USB = 2;
    public static final int INTERFACE_TYPE_BLUETOOTH_LOW_ENERGY = 3;
    public static final int INTERFACE_TYPE_WIFI_DIRECT = 4;
    public static final int MESSAGE_READ = 100;
    public static final int MESSAGE_BLUETOOTH_DEVICE_SET = 101;
    public static final int MESSAGE_USB_DEVICE_SET = 102;
    public static final int MESSAGE_NETWORK_DEVICE_SET = 103;
    public static final int MESSAGE_OUTPUT_COMPLETE = 104;
    public static final int MESSAGE_STATE_CHANGE = 105;
    public static final int MESSAGE_DEVICE_NAME = 106;
    public static final int MESSAGE_TOAST = 107;
    public static final int MESSAGE_LOG = 108;
    @NotNull
    public static final String DEVICE_NAME = "device_name";
    @NotNull
    public static final String TOAST = "toast";
    @NotNull
    public static final String LOG = "log";
    public static final int PROCESS_NONE = 0;
    public static final int PROCESS_GET_STATUS = 1;
    public static final int PROCESS_EXECUTE_DIRECT_IO = 2;
    public static final int PROCESS_OUTPUT_COMPLETE = 3;
    public static final int PROCESS_GET_INFORMATION_MODEL_NAME = 4;
    public static final int PROCESS_GET_INFORMATION_FIRMWARE_VERSION = 5;
    public static final int PROCESS_GET_INFORMATION_SERIAL_NUMBER = 6;
    public static final int FONT_SIZE_6 = 48;
    public static final int FONT_SIZE_8 = 49;
    public static final int FONT_SIZE_10 = 50;
    public static final int FONT_SIZE_12 = 51;
    public static final int FONT_SIZE_15 = 52;
    public static final int FONT_SIZE_20 = 53;
    public static final int FONT_SIZE_30 = 54;
    public static final int FONT_SIZE_14 = 55;
    public static final int FONT_SIZE_18 = 56;
    public static final int FONT_SIZE_24 = 57;
    public static final int FONT_SIZE_KOREAN1 = 97;
    public static final int FONT_SIZE_KOREAN2 = 98;
    public static final int FONT_SIZE_KOREAN3 = 99;
    public static final int FONT_SIZE_KOREAN4 = 100;
    public static final int FONT_SIZE_KOREAN5 = 101;
    public static final int FONT_SIZE_KOREAN6 = 102;
    public static final int FONT_SIZE_GB2312 = 109;
    public static final int FONT_SIZE_BIG5 = 110;
    public static final int FONT_SIZE_SHIFT_JIS = 106;
    public static final int ROTATION_NONE = 0;
    public static final int ROTATION_90_DEGREES = 1;
    public static final int ROTATION_180_DEGREES = 2;
    public static final int ROTATION_270_DEGREES = 3;
    public static final int TEXT_ALIGNMENT_NONE = 48;
    public static final int TEXT_ALIGNMENT_LEFT = 70;
    public static final int TEXT_ALIGNMENT_RIGHT = 76;
    public static final int TEXT_ALIGNMENT_RIGHT_TO_LEFT = 82;
    public static final int INTERNATIONAL_CHARACTER_SET_USA = 0;
    public static final int INTERNATIONAL_CHARACTER_SET_FRANCE = 1;
    public static final int INTERNATIONAL_CHARACTER_SET_GERMANY = 2;
    public static final int INTERNATIONAL_CHARACTER_SET_UK = 3;
    public static final int INTERNATIONAL_CHARACTER_SET_DENMARK1 = 4;
    public static final int INTERNATIONAL_CHARACTER_SET_SWEDEN = 5;
    public static final int INTERNATIONAL_CHARACTER_SET_ITALY = 6;
    public static final int INTERNATIONAL_CHARACTER_SET_SPAIN1 = 7;
    public static final int INTERNATIONAL_CHARACTER_SET_NORWAY = 8;
    public static final int INTERNATIONAL_CHARACTER_SET_DENMARK2 = 9;
    public static final int INTERNATIONAL_CHARACTER_SET_JAPAN = 10;
    public static final int INTERNATIONAL_CHARACTER_SET_SPAIN2 = 11;
    public static final int INTERNATIONAL_CHARACTER_SET_LATIN_AMERICA = 12;
    public static final int INTERNATIONAL_CHARACTER_SET_KOREA = 13;
    public static final int INTERNATIONAL_CHARACTER_SET_SLOVENIA_CROATIA = 14;
    public static final int INTERNATIONAL_CHARACTER_SET_CHINA = 15;
    public static final int CODE_PAGE_CP437_USA = 0;
    public static final int CODE_PAGE_CP850_LATIN1 = 1;
    public static final int CODE_PAGE_CP852_LATIN2 = 2;
    public static final int CODE_PAGE_CP860_PORTUGUESE = 3;
    public static final int CODE_PAGE_CP863_CANADIAN_FRENCH = 4;
    public static final int CODE_PAGE_CP865_NORDIC = 5;
    public static final int CODE_PAGE_WCP1252_LATIN1 = 6;
    public static final int CODE_PAGE_CP865_WCP1252_EUROPEAN_COMBINED = 7;
    public static final int CODE_PAGE_CP857_TURKISH = 8;
    public static final int CODE_PAGE_CP737_GREEK = 9;
    public static final int CODE_PAGE_WCP1250_LATIN2 = 10;
    public static final int CODE_PAGE_WCP1253_GREEK = 11;
    public static final int CODE_PAGE_WCP1254_TURKISH = 12;
    public static final int CODE_PAGE_CP855_CYRILLIC = 13;
    public static final int CODE_PAGE_CP862_HEBREW = 14;
    public static final int CODE_PAGE_CP866_CYRILLIC = 15;
    public static final int CODE_PAGE_WCP1251_CYRILLIC = 16;
    public static final int CODE_PAGE_WCP1255_HEBREW = 17;
    public static final int CODE_PAGE_CP928_GREEK = 18;
    public static final int CODE_PAGE_CP864_ARABIC = 19;
    public static final int CODE_PAGE_CP775_BALTIC = 20;
    public static final int CODE_PAGE_WCP1257_BALTIC = 21;
    public static final int CODE_PAGE_CP858_LATIN1_EURO = 22;
    public static final int VECTOR_FONT_ASCII = 85;
    public static final int VECTOR_FONT_KS5601 = 75;
    public static final int VECTOR_FONT_BIG5 = 66;
    public static final int VECTOR_FONT_GB2312 = 71;
    public static final int VECTOR_FONT_SHIFT_JIS = 74;
    public static final int VECTOR_FONT_OCR_A = 97;
    public static final int VECTOR_FONT_OCR_B = 98;
    public static final int VECTOR_FONT_TEXT_DIRECTION_LEFT_TO_RIGHT = 0;
    public static final int VECTOR_FONT_TEXT_DIRECTION_RIGHT_TO_LEFT = 1;
    public static final int VECTOR_FONT_TEXT_ALIGNMENT_LEFT = 76;
    public static final int VECTOR_FONT_TEXT_ALIGNMENT_RIGHT = 82;
    public static final int VECTOR_FONT_TEXT_ALIGNMENT_CENTER = 67;
    public static final int MEDIA_TYPE_GAP = 71;
    public static final int MEDIA_TYPE_CONTINUOUS = 67;
    public static final int MEDIA_TYPE_BLACK_MARK = 66;
    public static final int PRINTING_TYPE_DIRECT_THERMAL = 100;
    public static final int PRINTING_TYPE_THERMAL_TRANSFER = 116;
    public static final int SPEED_25IPS = 0;
    public static final int SPEED_30IPS = 1;
    public static final int SPEED_40IPS = 2;
    public static final int SPEED_50IPS = 3;
    public static final int SPEED_60IPS = 4;
    public static final int SPEED_70IPS = 5;
    public static final int SPEED_80IPS = 6;
    public static final int ORIENTATION_TOP_TO_BOTTOM = 84;
    public static final int ORIENTATION_BOTTOM_TO_TOP = 66;
    public static final int STATUS_NORMAL = 0;
    public static final byte STATUS_1ST_BYTE_PAPER_EMPTY = Byte.MIN_VALUE;
    public static final byte STATUS_1ST_BYTE_COVER_OPEN = 64;
    public static final byte STATUS_1ST_BYTE_CUTTER_JAMMED = 32;
    public static final byte STATUS_1ST_BYTE_TPH_OVERHEAT = 16;
    public static final byte STATUS_1ST_BYTE_AUTO_SENSING_FAILURE = 8;
    public static final byte STATUS_1ST_BYTE_RIBBON_END_ERROR = 4;
    public static final byte STATUS_2ND_BYTE_BUILDING_IN_IMAGE_BUFFER = Byte.MIN_VALUE;
    public static final byte STATUS_2ND_BYTE_PRINTING_IN_IMAGE_BUFFER = 64;
    public static final byte STATUS_2ND_BYTE_PAUSED_IN_PEELER_UNIT = 32;
    public static final int PRINTER_INFORMATION_MODEL_NAME = 0;
    public static final int PRINTER_INFORMATION_FIRMWARE_VERSION = 2;
    public static final int PRINTER_INFORMATION_SERIAL_NUMBER = 4;
    public static final int HRI_NOT_PRINTED = 0;
    public static final int HRI_BELOW_FONT_SIZE_1 = 1;
    public static final int HRI_ABOVE_FONT_SIZE_1 = 2;
    public static final int HRI_BELOW_FONT_SIZE_2 = 3;
    public static final int HRI_ABOVE_FONT_SIZE_2 = 4;
    public static final int HRI_BELOW_FONT_SIZE_3 = 5;
    public static final int HRI_ABOVE_FONT_SIZE_3 = 6;
    public static final int HRI_BELOW_FONT_SIZE_4 = 7;
    public static final int HRI_ABOVE_FONT_SIZE_4 = 8;
    public static final int BARCODE_CODE39 = 0;
    public static final int BARCODE_CODE128 = 1;
    public static final int BARCODE_I2OF5 = 2;
    public static final int BARCODE_CODABAR = 3;
    public static final int BARCODE_CODE93 = 4;
    public static final int BARCODE_UPC_A = 5;
    public static final int BARCODE_UPC_E = 6;
    public static final int BARCODE_EAN13 = 7;
    public static final int BARCODE_EAN8 = 8;
    public static final int BARCODE_UCC_EAN128 = 9;
    public static final int BARCODE_CODE11 = 10;
    public static final int BARCODE_PLANET = 11;
    public static final int BARCODE_INDUSTRIAL_2OF5 = 12;
    public static final int BARCODE_STANDARD_2OF5 = 13;
    public static final int BARCODE_LOGMARS = 14;
    public static final int BARCODE_UPC_EAN_EXTENSIONS = 15;
    public static final int BARCODE_POSTNET = 16;
    public static final int HRI_NOT_PRINT = 0;
    public static final int HRI_BELOW_BARCODE = 1;
    public static final int HRI_ABOVE_BARCODE = 2;
    public static final int MAXICODE_MODE0 = 0;
    public static final int MAXICODE_MODE2 = 2;
    public static final int MAXICODE_MODE3 = 3;
    public static final int MAXICODE_MODE4 = 4;
    public static final int PDF417_ERROR_CORRECTION_LEVEL0 = 0;
    public static final int PDF417_ERROR_CORRECTION_LEVEL1 = 1;
    public static final int PDF417_ERROR_CORRECTION_LEVEL2 = 2;
    public static final int PDF417_ERROR_CORRECTION_LEVEL3 = 3;
    public static final int PDF417_ERROR_CORRECTION_LEVEL4 = 4;
    public static final int PDF417_ERROR_CORRECTION_LEVEL5 = 5;
    public static final int PDF417_ERROR_CORRECTION_LEVEL6 = 6;
    public static final int PDF417_ERROR_CORRECTION_LEVEL7 = 7;
    public static final int PDF417_ERROR_CORRECTION_LEVEL8 = 8;
    public static final int PDF417_HRI_NOT_PRINTED = 0;
    public static final int PDF417_HRI_BELOW_BARCODE = 1;
    public static final int DATA_COMPRESSION_TEXT = 0;
    public static final int DATA_COMPRESSION_NUMERIC = 1;
    public static final int DATA_COMPRESSION_BINARY = 2;
    public static final int BARCODE_ORIGIN_POINT_CENTER = 0;
    public static final int BARCODE_ORIGIN_POINT_UPPER_LEFT = 1;
    public static final int QR_CODE_MODEL1 = 1;
    public static final int QR_CODE_MODEL2 = 2;
    public static final int ECC_LEVEL_7 = 76;
    public static final int ECC_LEVEL_15 = 77;
    public static final int ECC_LEVEL_25 = 81;
    public static final int ECC_LEVEL_30 = 72;
    public static final int CODE49_HRI_NOT_PRINTED = 0;
    public static final int CODE49_HRI_BELOW_BARCODE = 1;
    public static final int CODE49_HRI_ABOVE_BARCODE = 2;
    public static final int CODE49_STRING_MODE_REGULAR_ALPHANUMERIC = 0;
    public static final int CODE49_STRING_MODE_MULTIPLE_READ_ALPHANUMERIC = 1;
    public static final int CODE49_STRING_MODE_REGULAR_NUMERIC = 2;
    public static final int CODE49_STRING_MODE_GROUP_ALPHANUMERIC = 3;
    public static final int CODE49_STRING_MODE_REGULAR_ALPHANUMERIC_SHIFT1 = 4;
    public static final int CODE49_STRING_MODE_REGULAR_ALPHANUMERIC_SHIFT2 = 5;
    public static final int CODE49_STRING_MODE_AUTOMATIC_MODE = 7;
    public static final char CODABLOCK_MODE_A = 'A';
    public static final char CODABLOCK_MODE_E = 'E';
    public static final char CODABLOCK_MODE_F = 'F';
    public static final int MSI_BARCODE_CHECKDIGIT_NONE = 0;
    public static final int MSI_BARCODE_CHECKDIGIT_1MOD10 = 1;
    public static final int MSI_BARCODE_CHECKDIGIT_2MOD10 = 2;
    public static final int MSI_BARCODE_CHECKDIGIT_1MOD11_AND_1MOD_10 = 3;
    public static final int MSI_BARCODE_HRI_NOT_PRINTED = 0;
    public static final int MSI_BARCODE_HRI_BELOW_BARCODE = 1;
    public static final int MSI_BARCODE_HRI_ABOVE_BARCODE = 2;
    public static final int PLESSEY_BARCODE_HRI_NOT_PRINTED = 0;
    public static final int PLESSEY_BARCODE_HRI_BELOW_BARCODE = 1;
    public static final int PLESSEY_BARCODE_HRI_ABOVE_BARCODE = 2;
    public static final int BARCODE_TYPE_RSS14 = 0;
    public static final int BARCODE_TYPE_RSS14_TRUNCATED = 1;
    public static final int BARCODE_TYPE_RSS14_STACKED = 2;
    public static final int BARCODE_TYPE_RSS14_STACKED_OMNIDIRECTIONAL = 3;
    public static final int BARCODE_TYPE_RSS_LIMITIED = 4;
    public static final int BARCODE_TYPE_RSS_EXPANDED = 5;
    public static final int BARCODE_TYPE_RSS_UPCA = 6;
    public static final int BARCODE_TYPE_RSS_UPCE = 7;
    public static final int BARCODE_TYPE_RSS_EAN13 = 8;
    public static final int BARCODE_TYPE_RSS_EAN8 = 9;
    public static final int BARCODE_TYPE_RSS_UCC_EAN128_CCAB = 10;
    public static final int BARCODE_TYPE_RSS_UCC_EAN128_CCC = 11;
    public static final int BLOCK_OPTION_LINE_OVERWRITING = 79;
    public static final int BLOCK_OPTION_LINE_EXCLUSIVE_OR = 69;
    public static final int BLOCK_OPTION_LINE_DELETE = 68;
    public static final int BLOCK_OPTION_SLOPE = 83;
    public static final int BLOCK_OPTION_BOX = 66;
    public static final int CIRCLE_SIZE_DIAMETER5 = 1;
    public static final int CIRCLE_SIZE_DIAMETER7 = 2;
    public static final int CIRCLE_SIZE_DIAMETER9 = 3;
    public static final int CIRCLE_SIZE_DIAMETER11 = 4;
    public static final int CIRCLE_SIZE_DIAMETER13 = 5;
    public static final int CIRCLE_SIZE_DIAMETER21 = 6;
    public static final int LABEL_IMAGE_NONE = 0;
    public static final int LABEL_IMAGE_RLE = 1;
    public static final int LABEL_IMAGE_LZMA = 2;
    @NotNull
    public static final String REWINDER_ENABLE = "RWDy";
    @NotNull
    public static final String REWINDER_DISABLE = "RWDn";
    private static int LAST_ERROR;
    private static boolean transactionPrintMode;
    private static int CODEPAGE;
    public static final int ERROR_CODE_PRINTER_NOT_SUPPORTED = 1007;
    public static final int ERROR_CODE_FILE_NOT_FOUND = -2;
    public static final int ERROR_CODE_I_O = -3;
    public static final int ERROR_PDF_RENDER_IS_NULL = -4;
    public static final int RC_SUCCESS = 0;
    public static final int RC_FAIL = -1;
    public static final int RC_DEVICE_ALREADY_CONNECTED = 1;
    public static final int RC_DEVICE_CONNECTION_LOSS = 4;
    public static final int RC_DEVICE_CONNECTION_FAIL = 1000;
    public static final int RC_INVALID_PARAMETER = 1004;
    public static final int RC_DEVICE_NOT_CONNECTED = 1006;
    public static final int RC_NOT_SUPPORTED = 1007;
    public static final int RC_CONNECTION_TIMEOUT = 1011;
    public static final int RC_FAIL_WRITE_PORT = 50;
    public static final int RC_FAIL_READ_PORT = 100;
    public static final int RC_BT_NOT_AVAILABLE = 1021;
    public static final int RC_BT_NOT_ENABLED = 1022;
    public static final int RC_USB_NOT_AVAILABLE = 202;
    public static final int RC_SOCKET_ERROR = 2000;

    /* compiled from: BixolonLabelPrinter.kt */
    @Metadata(mv = {1, 5, 1}, k = 1, xi = 48, d1 = {"��\u0018\n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0010\u0002\n��\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018��2\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0003H&¨\u0006\u0007"}, d2 = {"Lcom/bixolon/labelprinter/BixolonLabelPrinter$TransferFileListener;", "", "onFailed", "", "result", "", "onSuccess", "BixolonLabelSDK_release"})
    /* loaded from: BixolonLabelPrinterLibrary_V2.0.4.jar:com/bixolon/labelprinter/BixolonLabelPrinter$TransferFileListener.class */
    public interface TransferFileListener {
        void onSuccess();

        void onFailed(int i);
    }

    public BixolonLabelPrinter(@NotNull Context context, @NotNull Handler uiHandler, @Nullable Looper looper) {
        Context context2;
        License license;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(uiHandler, "uiHandler");
        this.context = context;
        this.uiHandler = uiHandler;
        this.looper = looper;
        this.pdfDpi = 200;
        this.isPDFAvailable = true;
        this.slcsEmul = SLCSEmul.getInstance();
        this.bxlQueue = new BXLQueue();
        this.printerControl = new PrinterControl(this.context, this.uiHandler, this.looper);
        try {
            System.loadLibrary("bxl_common");
            License license2 = License.getInstance();
            Intrinsics.checkNotNullExpressionValue(license2, "getInstance()");
            this.pdfLicense = license2;
            context2 = this.context;
            license = this.pdfLicense;
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
        if (license == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pdfLicense");
            throw null;
        }
        this.pdfCore = new PdfCore(context2, license);
        License license3 = this.pdfLicense;
        if (license3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pdfLicense");
            throw null;
        }
        license3.setIsLicenseKey(true);
        LogService.LogI(2, TAG, "++ init 2.0.4 ++");
        LogService.LogI(2, TAG, Intrinsics.stringPlus("++ Android VERSION : ", Build.VERSION.RELEASE));
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BixolonLabelPrinter(@NotNull Context context) {
        this(context, new Handler(), null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @NotNull
    public final Set<BluetoothDevice> findBluetoothPrinters() {
        LogService.LogI(2, TAG, "++ findBluetoothPrinters() ++");
        this.uiHandler.obtainMessage(101, BXLBluetooth.getPairedDevices()).sendToTarget();
        Set<BluetoothDevice> pairedDevices = BXLBluetooth.getPairedDevices();
        Intrinsics.checkNotNullExpressionValue(pairedDevices, "getPairedDevices()");
        return pairedDevices;
    }

    @NotNull
    public final String findNetworkPrinters(int timeout) {
        LogService.LogI(2, TAG, "++ findNetworkPrinter(" + timeout + ") ++ ");
        Object systemService = this.context.getApplicationContext().getSystemService("wifi");
        if (systemService == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.net.wifi.WifiManager");
        }
        WifiManager wifiManager = (WifiManager) systemService;
        WifiManager.MulticastLock multicastLock = wifiManager.createMulticastLock("multicast_lock");
        multicastLock.setReferenceCounted(true);
        multicastLock.acquire();
        BXLNetwork.setWifiSearchOption(timeout, 1.0f, (byte) 3);
        String networkPrinters = BXLNetwork.getNetworkPrinters();
        this.uiHandler.obtainMessage(MESSAGE_NETWORK_DEVICE_SET, networkPrinters).sendToTarget();
        if (multicastLock != null) {
            multicastLock.release();
        }
        Intrinsics.checkNotNullExpressionValue(networkPrinters, "networkPrinters");
        return networkPrinters;
    }

    @NotNull
    public final String findUsbPrinters() {
        LogService.LogI(2, TAG, "++ findUsbPrinters() ++ ");
        Object systemService = this.context.getApplicationContext().getSystemService("usb");
        if (systemService == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.hardware.usb.UsbManager");
        }
        UsbManager usbManager = (UsbManager) systemService;
        Set usbPrinters = BXLUsbDevice.refreshUsbDevicesList(this.context.getApplicationContext(), false);
        this.uiHandler.obtainMessage(102, usbPrinters).sendToTarget();
        return usbPrinters.toString();
    }

    @NotNull
    public final String connect() {
        LogService.LogI(2, TAG, "++ connect to usb device ++");
        return connect("", 2, 9100, 0);
    }

    @NotNull
    public final String connect(@NotNull String address) {
        Intrinsics.checkNotNullParameter(address, "address");
        return connect(address, 0, 9100, 0);
    }

    @NotNull
    public final String connect(@NotNull UsbDevice device) {
        Intrinsics.checkNotNullParameter(device, "device");
        LogService.LogI(2, TAG, "++ connect to usb device : " + device + " ++");
        return connect(null, 2, 9100, 0);
    }

    @NotNull
    public final String connect(@NotNull UsbDevice device, @NotNull String deviceName) {
        Intrinsics.checkNotNullParameter(device, "device");
        Intrinsics.checkNotNullParameter(deviceName, "deviceName");
        LogService.LogI(2, TAG, "++ connect to usb device : " + deviceName + " ++");
        return connect(deviceName, 2, 9100, 0);
    }

    @NotNull
    public final String connect(@NotNull String address, int type) {
        Intrinsics.checkNotNullParameter(address, "address");
        return connect(address, type, 9100, 0);
    }

    @NotNull
    public final String connect(@NotNull String address, int port, int timeout) {
        Intrinsics.checkNotNullParameter(address, "address");
        return connect(address, 1, port, timeout);
    }

    @NotNull
    public final String connect(@Nullable String address, int type, int port, int timeout) {
        LogService.LogI(2, TAG, "++ connect(" + type + ", " + ((Object) address) + ", " + port + ", " + timeout + ") ++");
        return this.printerControl.printerConnect(type, address, port, timeout);
    }

    public final boolean isConnected() {
        LogService.LogI(2, TAG, "++ isConnected() ++");
        return this.printerControl.isConnected();
    }

    public final void disconnect() {
        LogService.LogI(2, TAG, "++ disconnect() ++");
        this.printerControl.printerDisconnect();
        this.uiHandler.obtainMessage(MESSAGE_STATE_CHANGE, 0, 0).sendToTarget();
    }

    public final void disableInactivityTime(boolean disable) {
        LogService.LogI(2, TAG, "++ disableInactivityTime( + " + disable + " + ) ++");
        this.printerControl.disableInactivityTime(disable);
    }

    public final int drawText(@NotNull String data, int horizontalPosition, int verticalPosition, int fontSize, int horizontalMultiplier, int verticalMultiplier, int rightSpace, int rotation, boolean reverse, boolean bold, int alignment) {
        byte b;
        byte b2;
        Intrinsics.checkNotNullParameter(data, "data");
        LogService.LogI(2, TAG, "++ drawText(" + data + ", " + horizontalPosition + ", " + verticalPosition + ", " + fontSize + ", " + horizontalMultiplier + ", " + verticalMultiplier + ", " + rightSpace + ", " + rotation + ", " + reverse + ", " + bold + ", " + alignment + ") ++");
        if (data.length() == 0) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        if (!(1 <= horizontalMultiplier ? horizontalMultiplier <= 6 : false)) {
            Companion companion2 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        if (!(1 <= verticalMultiplier ? verticalMultiplier <= 6 : false)) {
            Companion companion3 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        if (!(0 <= rotation ? rotation <= 3 : false)) {
            Companion companion4 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        int fontType = 0;
        int cp = convertInternalCodePage(getCodePage());
        if (97 <= fontSize ? fontSize <= 102 : false) {
            fontType = 1;
        } else if (fontSize == 110 || fontSize == 109) {
            fontType = 2;
        } else if (fontSize == 106) {
            fontType = 3;
        }
        if (fontType != 0) {
            cp = convertInternalCodePage(fontSize);
        }
        SLCSEmul sLCSEmul = this.slcsEmul;
        byte b3 = (byte) fontSize;
        String valueOf = String.valueOf(rightSpace);
        if (reverse) {
            b = (byte) 82;
        } else {
            b = (byte) 78;
        }
        if (bold) {
            b2 = (byte) 66;
        } else {
            b2 = (byte) 78;
        }
        sLCSEmul.AddDeviceFont(horizontalPosition, verticalPosition, b3, horizontalMultiplier, verticalMultiplier, valueOf, rotation, b, b2, (byte) alignment, data, cp);
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int drawVectorFontText(@NotNull String data, int horizontalPosition, int verticalPosition, int font, int width, int height, int rightSpace, boolean bold, boolean reverse, boolean italic, int rotation, int alignment, int direction) {
        Intrinsics.checkNotNullParameter(data, "data");
        LogService.LogI(2, TAG, "++ drawVectorFontText(" + data + ", " + horizontalPosition + ", " + verticalPosition + ", " + font + ", " + width + ", " + height + ", " + rightSpace + ", " + bold + ", " + reverse + ", " + italic + ", " + rotation + ", " + alignment + ", " + direction + ") ++");
        int fontType = 0;
        int cp = convertInternalCodePage(getCodePage());
        switch (font) {
            case 66:
            case 71:
                fontType = 2;
                break;
            case VECTOR_FONT_SHIFT_JIS /* 74 */:
                fontType = 3;
                break;
            case VECTOR_FONT_KS5601 /* 75 */:
                fontType = 1;
                break;
            case VECTOR_FONT_ASCII /* 85 */:
            case 97:
            case 98:
                fontType = 0;
                break;
        }
        if (data.length() == 0) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        if (fontType != 0) {
            cp = convertInternalCodePage(font);
        }
        this.slcsEmul.AddVectorFont(horizontalPosition, verticalPosition, (byte) font, width, height, String.valueOf(rightSpace), bold, reverse, italic, rotation, (byte) alignment, direction, data, cp);
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int draw1dBarcode(@NotNull String data, int horizontalPosition, int verticalPosition, int barcodeSelection, int narrowBarWidth, int wideBarWidth, int height, int rotation, int hri, int quietZoneWidth) {
        Intrinsics.checkNotNullParameter(data, "data");
        LogService.LogI(2, TAG, "++ draw1dBarcode(" + data + ", " + horizontalPosition + ". " + verticalPosition + ", " + barcodeSelection + ", " + narrowBarWidth + ", " + wideBarWidth + ", " + height + ", " + rotation + ", " + hri + ", " + quietZoneWidth + ") ++");
        if (data.length() == 0) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        this.slcsEmul.Add1DBarcode(horizontalPosition, verticalPosition, barcodeSelection, narrowBarWidth, wideBarWidth, height, rotation, hri, quietZoneWidth, data);
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int drawMaxicode(@NotNull String data, int horizontalPosition, int verticalPosition, int mode) {
        Intrinsics.checkNotNullParameter(data, "data");
        LogService.LogI(2, TAG, "++ drawMaxicode(" + data + ", " + horizontalPosition + ", " + verticalPosition + ", " + mode + ") ++");
        if (data.length() == 0) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        if (mode != 0) {
            if (!(2 <= mode ? mode <= 4 : false)) {
                Companion companion2 = Companion;
                LAST_ERROR = RC_INVALID_PARAMETER;
                return RC_INVALID_PARAMETER;
            }
        }
        this.slcsEmul.AddMaxiCode(horizontalPosition, verticalPosition, mode, data);
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int drawPdf417(@NotNull String data, int horizontalPosition, int verticalPosition, int maxRow, int maxColumn, int errorCorrection, int compression, int hri, int originPoint, int width, int height, int rotation) {
        Intrinsics.checkNotNullParameter(data, "data");
        LogService.LogI(2, TAG, "++ drawPdf417(" + data + ", " + horizontalPosition + ", " + verticalPosition + ", " + maxRow + ", " + maxColumn + ", " + errorCorrection + ", " + compression + ", " + hri + ", " + originPoint + ", " + width + ", " + height + ", " + rotation + ") ++");
        if (data.length() == 0) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        } else if (maxRow < 3 || maxRow > 90) {
            Companion companion2 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        } else if (maxColumn < 1 || maxColumn > 30) {
            Companion companion3 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        } else {
            if (!(0 <= errorCorrection ? errorCorrection <= 8 : false)) {
                Companion companion4 = Companion;
                LAST_ERROR = RC_INVALID_PARAMETER;
                return RC_INVALID_PARAMETER;
            }
            if (!(0 <= compression ? compression <= 2 : false)) {
                Companion companion5 = Companion;
                LAST_ERROR = RC_INVALID_PARAMETER;
                return RC_INVALID_PARAMETER;
            }
            if (!(2 <= width ? width <= 9 : false)) {
                Companion companion6 = Companion;
                LAST_ERROR = RC_INVALID_PARAMETER;
                return RC_INVALID_PARAMETER;
            }
            if (!(0 <= height ? height <= 99 : false)) {
                Companion companion7 = Companion;
                LAST_ERROR = RC_INVALID_PARAMETER;
                return RC_INVALID_PARAMETER;
            }
            if (!(0 <= rotation ? rotation <= 3 : false)) {
                Companion companion8 = Companion;
                LAST_ERROR = RC_INVALID_PARAMETER;
                return RC_INVALID_PARAMETER;
            }
            this.slcsEmul.AddPDF417(horizontalPosition, verticalPosition, maxRow, maxColumn, errorCorrection, compression, hri == 1, originPoint == 0 ? 0 : 1, width, height, rotation, data);
            byte[] PopAll = this.slcsEmul.PopAll();
            Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
            return inputCommand(PopAll);
        }
    }

    public final int drawQrCode(@NotNull String data, int horizontalPosition, int verticalPosition, int model, int eccLevel, int size, int rotation) {
        Intrinsics.checkNotNullParameter(data, "data");
        LogService.LogI(2, TAG, "++ drawQrCode(" + data + ", " + horizontalPosition + ", " + verticalPosition + ", " + model + ", " + eccLevel + ", " + size + ", " + rotation + ") ++");
        if (data.length() == 0) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        if (!(1 <= model ? model <= 2 : false)) {
            Companion companion2 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        } else if (eccLevel != 76 && eccLevel != 77 && eccLevel != 81 && eccLevel != 72) {
            Companion companion3 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        } else {
            this.slcsEmul.AddQRCode(horizontalPosition, verticalPosition, model, eccLevel, size, rotation, data);
            byte[] PopAll = this.slcsEmul.PopAll();
            Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
            return inputCommand(PopAll);
        }
    }

    public final int drawDataMatrix(@NotNull String data, int horizontalPosition, int verticalPosition, int size, int rotation) {
        Intrinsics.checkNotNullParameter(data, "data");
        LogService.LogI(2, TAG, "++ drawDataMatrix(" + data + ", " + horizontalPosition + ", " + verticalPosition + ", " + size + ", " + rotation + ')');
        if (data.length() == 0) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        if (!(0 <= rotation ? rotation <= 3 : false)) {
            Companion companion2 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        if (!(1 <= size ? size <= 4 : false)) {
            Companion companion3 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        this.slcsEmul.AddDataMatrix(horizontalPosition, verticalPosition, size, false, rotation, data);
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int drawAztec(@NotNull String data, int horizontalPosition, int verticalPosition, int size, boolean extendedChannel, int eccLevel, boolean menuSymbol, int numberOfSymbols, @Nullable String optionalID, int rotation) {
        Intrinsics.checkNotNullParameter(data, "data");
        LogService.LogI(2, TAG, "++ drawAztec(" + data + ", " + horizontalPosition + ", " + verticalPosition + ", " + size + ", " + extendedChannel + ", " + rotation + ") ++");
        if (data.length() == 0) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        if (!(0 <= rotation ? rotation <= 3 : false)) {
            Companion companion2 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        if (!(1 <= size ? size <= 10 : false)) {
            Companion companion3 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        this.slcsEmul.AddAztec(horizontalPosition, verticalPosition, size, extendedChannel, eccLevel, menuSymbol, numberOfSymbols, optionalID, rotation, data);
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int drawCode49(@NotNull String data, int horizontalPosition, int verticalPosition, int widthNarrow, int widthWide, int height, int hri, int startingMode, int rotation) {
        Intrinsics.checkNotNullParameter(data, "data");
        LogService.LogI(2, TAG, "++ drawCode49(" + data + ", " + horizontalPosition + ", " + verticalPosition + ", " + widthNarrow + ", " + widthWide + ", " + height + ", " + hri + ", " + startingMode + ", " + rotation + ") ++");
        if (data.length() == 0) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        if (!(0 <= rotation ? rotation <= 3 : false)) {
            Companion companion2 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        if (!(0 <= hri ? hri <= 2 : false)) {
            Companion companion3 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        if (!(0 <= startingMode ? startingMode <= 7 : false)) {
            Companion companion4 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        this.slcsEmul.AddCode49(horizontalPosition, verticalPosition, widthNarrow, widthWide, height, hri, startingMode, rotation, data);
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int drawCodaBlock(@NotNull String data, int horizontalPosition, int verticalPosition, int widthNarrow, int widthWide, int height, boolean securityLevel, int dataColumns, char mode, int encode) {
        Intrinsics.checkNotNullParameter(data, "data");
        LogService.LogI(2, TAG, "++ drawCodaBlock(" + data + ", " + horizontalPosition + ", " + verticalPosition + ", " + widthNarrow + ", " + widthWide + ", " + height + ", " + securityLevel + ", " + dataColumns + ", " + mode + ", " + encode + ") ++");
        if (data.length() == 0) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        } else if (mode != 'A' && mode != 'E' && mode != 'F') {
            Companion companion2 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        } else {
            this.slcsEmul.AddCodaBlock(horizontalPosition, verticalPosition, widthNarrow, widthWide, height, securityLevel, dataColumns, (byte) mode, encode, data);
            byte[] PopAll = this.slcsEmul.PopAll();
            Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
            return inputCommand(PopAll);
        }
    }

    public final int drawMicroPDF417(@NotNull String data, int horizontalPosition, int verticalPosition, int moduleWidth, int height, int mode, int rotation) {
        Intrinsics.checkNotNullParameter(data, "data");
        LogService.LogI(2, TAG, "++ drawMicroPDF417(" + data + ", " + horizontalPosition + ", " + verticalPosition + ", " + moduleWidth + ", " + height + ", " + mode + ", " + rotation + ") ++");
        if (data.length() == 0) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        if (!(0 <= rotation ? rotation <= 3 : false)) {
            Companion companion2 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        this.slcsEmul.AddMicroPDF417(horizontalPosition, verticalPosition, moduleWidth, height, mode, rotation, data);
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int drawIMBBarcode(@NotNull String data, int horizontalPosition, int verticalPosition, boolean hri, int rotation) {
        Intrinsics.checkNotNullParameter(data, "data");
        LogService.LogI(2, TAG, "++ drawIMBBarcode(" + data + ", " + horizontalPosition + ", " + verticalPosition + ", " + hri + rotation + ") ++");
        if (data.length() == 0) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        if (!(0 <= rotation ? rotation <= 3 : false)) {
            Companion companion2 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        this.slcsEmul.AddIMB(horizontalPosition, verticalPosition, rotation, hri, data);
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int drawMSIBarcode(@NotNull String data, int horizontalPosition, int verticalPosition, int widthNarrow, int widthWide, int height, int checkDigit, boolean printCheckDigit, int hri, int rotation) {
        Intrinsics.checkNotNullParameter(data, "data");
        LogService.LogI(2, TAG, "++ drawMSIBarcode(" + data + ", " + horizontalPosition + ", " + verticalPosition + ", " + widthNarrow + ", " + widthWide + ", " + height + ", " + checkDigit + ", " + printCheckDigit + ", " + hri + ", " + rotation + ") ++");
        if (data.length() == 0) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        if (!(0 <= rotation ? rotation <= 3 : false)) {
            Companion companion2 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        if (!(0 <= hri ? hri <= 2 : false)) {
            Companion companion3 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        this.slcsEmul.AddMSI(horizontalPosition, verticalPosition, widthNarrow, widthWide, height, checkDigit, printCheckDigit, rotation, hri, data);
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int drawPlesseyBarcode(@NotNull String data, int horizontalPosition, int verticalPosition, int widthNarrow, int widthWide, int height, boolean printCheckDigit, int hri, int rotation) {
        Intrinsics.checkNotNullParameter(data, "data");
        LogService.LogI(2, TAG, "++ drawPlesseyBarcode(" + data + ", " + horizontalPosition + ", " + verticalPosition + ", " + widthNarrow + ", " + widthWide + ", " + height + ", " + printCheckDigit + ", " + hri + ", " + rotation + ") ++");
        if (data.length() == 0) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        if (!(0 <= rotation ? rotation <= 3 : false)) {
            Companion companion2 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        if (!(0 <= hri ? hri <= 2 : false)) {
            Companion companion3 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        this.slcsEmul.AddPlessey(horizontalPosition, verticalPosition, widthNarrow, widthWide, height, printCheckDigit, rotation, hri, data);
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int drawTLC39Barcode(@NotNull String data, int horizontalPosition, int verticalPosition, int widthNarrow, int widthWide, int height, int rowHeightOfMicroPDF417, int narrowWidthOfMicroPDF417, int rotation) {
        Intrinsics.checkNotNullParameter(data, "data");
        LogService.LogI(2, TAG, "++ drawTLC39Barcode(" + data + ", " + horizontalPosition + ", " + verticalPosition + ", " + widthNarrow + ", " + widthWide + ", " + height + ", " + rowHeightOfMicroPDF417 + ", " + narrowWidthOfMicroPDF417 + ", " + rotation + ") ++");
        if (data.length() == 0) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        if (!(0 <= rotation ? rotation <= 3 : false)) {
            Companion companion2 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        this.slcsEmul.AddTLC39(horizontalPosition, verticalPosition, widthNarrow, widthWide, height, rowHeightOfMicroPDF417, narrowWidthOfMicroPDF417, rotation, data);
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int drawRSSBarcode(@NotNull String data, int horizontalPosition, int verticalPosition, int barcodeType, int magnification, int seperator, int BarHeight, int SegmentWidth, int rotation) {
        Intrinsics.checkNotNullParameter(data, "data");
        LogService.LogI(2, TAG, "++ drawRSSBarcode(" + data + ", " + horizontalPosition + ", " + verticalPosition + ", " + barcodeType + ", " + magnification + ", " + seperator + ", " + BarHeight + ", " + SegmentWidth + ", " + rotation + ") ++");
        if (data.length() == 0) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        if (!(0 <= rotation ? rotation <= 3 : false)) {
            Companion companion2 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        if (!(0 <= barcodeType ? barcodeType <= 11 : false)) {
            Companion companion3 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        this.slcsEmul.AddRSS(horizontalPosition, verticalPosition, barcodeType, magnification, seperator, BarHeight, SegmentWidth, rotation, data);
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int drawBlock(int horizontalStartPosition, int verticalStartPosition, int horizontalEndPosition, int verticalEndPosition, int option, int thickness) {
        LogService.LogI(2, TAG, "++ drawBlock(" + horizontalStartPosition + ", " + verticalStartPosition + ", " + horizontalEndPosition + ", " + verticalEndPosition + ", " + option + ", " + thickness + ") ++");
        if (horizontalStartPosition < 0) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        } else if (verticalStartPosition < 0) {
            Companion companion2 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        } else if (option != 79 && option != 69 && option != 68 && option != 83 && option != 66) {
            Companion companion3 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        } else {
            this.slcsEmul.AddDrawBlock(horizontalStartPosition, verticalStartPosition, horizontalEndPosition, verticalEndPosition, (byte) option, thickness);
            byte[] PopAll = this.slcsEmul.PopAll();
            Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
            return inputCommand(PopAll);
        }
    }

    public final int drawTowBlock(int horizontalStartPosition, int verticalStartPosition, int horizontalEndPosition, int verticalEndPosition, int option, int horizontalStartPositionSquare2, int verticalStartPositionSquare2, int horizontalEndPositionSquare2, int verticalEndPositionSquare2, int optionSquare2) {
        LogService.LogI(2, TAG, "++ drawTwoBlock(" + horizontalStartPosition + ", " + verticalStartPosition + ", " + horizontalEndPosition + ", " + verticalEndPosition + ", " + option + ", " + horizontalStartPositionSquare2 + ", " + verticalStartPositionSquare2 + ", " + horizontalEndPositionSquare2 + ", " + verticalEndPositionSquare2 + ", " + optionSquare2 + ") ++");
        if (horizontalStartPosition < 0) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        } else if (verticalStartPosition < 0) {
            Companion companion2 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        } else if (option != 79 && option != 69 && option != 68 && option != 83 && option != 66) {
            Companion companion3 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        } else if (horizontalStartPositionSquare2 < 0) {
            Companion companion4 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        } else if (verticalStartPositionSquare2 < 0) {
            Companion companion5 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        } else if (optionSquare2 != 79 && optionSquare2 != 69 && optionSquare2 != 68 && optionSquare2 != 83 && optionSquare2 != 66) {
            Companion companion6 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        } else {
            this.slcsEmul.AddDrawBlock(horizontalStartPosition, verticalStartPosition, horizontalEndPosition, verticalEndPosition, (byte) option, 0);
            this.slcsEmul.AddDrawBlock(horizontalStartPositionSquare2, verticalStartPositionSquare2, horizontalEndPositionSquare2, verticalEndPositionSquare2, (byte) optionSquare2, 0);
            byte[] PopAll = this.slcsEmul.PopAll();
            Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
            return inputCommand(PopAll);
        }
    }

    public final int drawCircle(int horizontalStartPosition, int verticalStartPosition, int size, int multiplier) {
        LogService.LogI(2, TAG, "++ drawCircle(" + horizontalStartPosition + ", " + verticalStartPosition + ", " + size + ", " + multiplier + ") ++");
        if (!(1 <= size ? size <= 6 : false)) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        if (!(1 <= multiplier ? multiplier <= 4 : false)) {
            Companion companion2 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        this.slcsEmul.AddDrawCircle(horizontalStartPosition, verticalStartPosition, size, multiplier);
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int drawBitmap(@NotNull String pathName, int horizontalStartPosition, int verticalStartPosition, int width, int level, boolean dithering) {
        Intrinsics.checkNotNullParameter(pathName, "pathName");
        LogService.LogI(2, TAG, "++ drawBitmap(" + pathName + ", " + horizontalStartPosition + ", " + verticalStartPosition + ", " + width + ") ++");
        if (pathName.length() == 0) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        LabelImage image = LabelImage.getInstance();
        if (image.Load(pathName)) {
            image.MakeLD(horizontalStartPosition, verticalStartPosition, width, dithering ? 1 : 0, 0, level, 30);
        }
        byte[] PopAll = image.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "image.PopAll()");
        return inputCommand(PopAll);
    }

    public final int drawBitmap(@NotNull String pathName, int horizontalStartPosition, int verticalStartPosition, int width, int level, boolean dithering, int brightness) {
        Intrinsics.checkNotNullParameter(pathName, "pathName");
        LogService.LogI(2, TAG, "++ drawBitmap(" + pathName + ", " + horizontalStartPosition + ", " + verticalStartPosition + ", " + width + ") ++");
        if (pathName.length() == 0) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        LabelImage image = LabelImage.getInstance();
        if (image.Load(pathName)) {
            image.MakeLD(horizontalStartPosition, verticalStartPosition, width, dithering ? 1 : 0, 0, level, brightness);
        }
        byte[] PopAll = image.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "image.PopAll()");
        return inputCommand(PopAll);
    }

    public final int drawBitmap(@NotNull Bitmap bitmap, int horizontalStartPosition, int verticalStartPosition, int width, int level, boolean dithering) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        LogService.LogI(2, TAG, "++ drawBitmap(" + bitmap + ", " + horizontalStartPosition + ", " + verticalStartPosition + ", " + width + ") ++");
        LabelImage image = LabelImage.getInstance();
        if (image.Load(bitmap)) {
            image.MakeLD(horizontalStartPosition, verticalStartPosition, width, dithering ? 1 : 0, 0, level, 30);
        }
        byte[] PopAll = image.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "image.PopAll()");
        return inputCommand(PopAll);
    }

    public final int drawBitmap(@NotNull Bitmap bitmap, int horizontalStartPosition, int verticalStartPosition, int width, int level, boolean dithering, int brightness) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        LogService.LogI(2, TAG, "++ drawBitmap(" + bitmap + ", " + horizontalStartPosition + ", " + verticalStartPosition + ", " + width + ") ++");
        LabelImage image = LabelImage.getInstance();
        if (image.Load(bitmap)) {
            image.MakeLD(horizontalStartPosition, verticalStartPosition, width, dithering ? 1 : 0, 0, level, brightness);
        }
        byte[] PopAll = image.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "image.PopAll()");
        return inputCommand(PopAll);
    }

    public final int drawBitmap(@NotNull Bitmap bitmap, int horizontalStartPosition, int verticalStartPosition, int width, int level, boolean dithering, int rotation, int brightness) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        LogService.LogI(2, TAG, "++ drawBitmap(" + bitmap + ", " + horizontalStartPosition + ", " + verticalStartPosition + ", " + width + ") ++");
        LabelImage image = LabelImage.getInstance();
        if (image.Load(bitmap)) {
            image.MakeLD(horizontalStartPosition, verticalStartPosition, width, dithering ? 1 : 0, rotation, level, brightness);
        }
        byte[] PopAll = image.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "image.PopAll()");
        return inputCommand(PopAll);
    }

    public final int drawCompressionImage(@NotNull String pathName, int horizontalStartPosition, int verticalStartPosition, int width, int level, boolean dithering) {
        Intrinsics.checkNotNullParameter(pathName, "pathName");
        LogService.LogI(2, TAG, "++ drawCompressionImage(" + pathName + ", " + horizontalStartPosition + ", " + verticalStartPosition + ", " + width + ") ++");
        if (pathName.length() == 0) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        LabelImage image = LabelImage.getInstance();
        if (image.Load(pathName)) {
            image.MakeLC(horizontalStartPosition, verticalStartPosition, width, dithering ? 1 : 0, 0, (level * 255) / 100, 50);
        }
        byte[] PopAll = image.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "image.PopAll()");
        return inputCommand(PopAll);
    }

    public final int drawCompressionImage(@NotNull String pathName, int horizontalStartPosition, int verticalStartPosition, int width, int level, boolean dithering, int brightness) {
        Intrinsics.checkNotNullParameter(pathName, "pathName");
        LogService.LogI(2, TAG, "++ drawCompressionImage(" + pathName + ", " + horizontalStartPosition + ", " + verticalStartPosition + ", " + width + ") ++");
        if (pathName.length() == 0) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        LabelImage image = LabelImage.getInstance();
        if (image.Load(pathName)) {
            image.MakeLC(horizontalStartPosition, verticalStartPosition, width, dithering ? 1 : 0, 0, (level * 255) / 100, brightness);
        }
        byte[] PopAll = image.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "image.PopAll()");
        return inputCommand(PopAll);
    }

    public final int drawCompressionImage(@NotNull Bitmap bitmap, int horizontalStartPosition, int verticalStartPosition, int width, int level, boolean dithering) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        LogService.LogI(2, TAG, "++ drawCompressionImage(" + bitmap + ", " + horizontalStartPosition + ", " + verticalStartPosition + ", " + width + ") ++");
        LabelImage image = LabelImage.getInstance();
        if (image.Load(bitmap)) {
            image.MakeLC(horizontalStartPosition, verticalStartPosition, width, dithering ? 1 : 0, 0, (level * 255) / 100, 50);
        }
        byte[] PopAll = image.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "image.PopAll()");
        return inputCommand(PopAll);
    }

    public final int drawCompressionImage(@NotNull Bitmap bitmap, int horizontalStartPosition, int verticalStartPosition, int width, int level, boolean dithering, int brightness) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        LogService.LogI(2, TAG, "++ drawCompressionImage(" + bitmap + ", " + horizontalStartPosition + ", " + verticalStartPosition + ", " + width + ") ++");
        LabelImage image = LabelImage.getInstance();
        if (image.Load(bitmap)) {
            image.MakeLC(horizontalStartPosition, verticalStartPosition, width, dithering ? 1 : 0, 0, (level * 255) / 100, brightness);
        }
        byte[] PopAll = image.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "image.PopAll()");
        return inputCommand(PopAll);
    }

    public final int drawCompressionImage(@NotNull Bitmap bitmap, int horizontalStartPosition, int verticalStartPosition, int width, int level, boolean dithering, int rotation, int brightness) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        LogService.LogI(2, TAG, "++ drawCompressionImage(" + bitmap + ", " + horizontalStartPosition + ", " + verticalStartPosition + ", " + width + ") ++");
        LabelImage image = LabelImage.getInstance();
        if (image.Load(bitmap)) {
            image.MakeLC(horizontalStartPosition, verticalStartPosition, width, dithering ? 1 : 0, rotation, (level * 255) / 100, brightness);
        }
        byte[] PopAll = image.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "image.PopAll()");
        return inputCommand(PopAll);
    }

    public final int drawImage(@NotNull Bitmap bitmap, int startPosX, int startPosY, int width, int threshold, int ditheringType, int compressType) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        LogService.LogI(2, TAG, "++ drawImage(" + bitmap + ", " + startPosX + ", " + startPosY + ", " + width + ", " + threshold + ", " + ditheringType + ", " + compressType + ") ++");
        LabelImage image = LabelImage.getInstance();
        if (image.Load(bitmap)) {
            switch (compressType) {
                case 0:
                    image.MakeLD(startPosX, startPosY, width, ditheringType, 0, threshold, 30);
                    break;
                case 1:
                    image.MakeLC(startPosX, startPosY, width, ditheringType, 0, threshold, 30);
                    break;
                case 2:
                    image.MakeLCL(startPosX, startPosY, width, ditheringType, 0, threshold, 30);
                    break;
            }
        }
        byte[] PopAll = image.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "image.PopAll()");
        return inputCommand(PopAll);
    }

    public final int drawImageFile(@NotNull String fileName, int startPosX, int startPosY, int width, int threshold, int ditheringType, int compressType) {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        LogService.LogI(2, TAG, "++ drawImageFile(" + fileName + ", " + startPosX + ", " + startPosY + ", " + width + ", " + threshold + ", " + ditheringType + ", " + compressType + ") ++");
        LabelImage image = LabelImage.getInstance();
        if (image.Load(fileName)) {
            switch (compressType) {
                case 0:
                    image.MakeLD(startPosX, startPosY, width, ditheringType, 0, threshold, 50);
                    break;
                case 1:
                    image.MakeLC(startPosX, startPosY, width, ditheringType, 0, threshold, 50);
                    break;
                case 2:
                    image.MakeLCL(startPosX, startPosY, width, ditheringType, 0, threshold, 50);
                    break;
            }
        }
        byte[] PopAll = image.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "image.PopAll()");
        return inputCommand(PopAll);
    }

    public final int drawImage(@NotNull Bitmap bitmap, int startPosX, int startPosY, int width, int threshold, int ditheringType, int compressType, int rotation) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        LogService.LogI(2, TAG, "++ drawImage(" + bitmap + ", " + startPosX + ", " + startPosY + ", " + width + ", " + threshold + ", " + ditheringType + ", " + compressType + ", " + rotation + ") ++");
        LabelImage image = LabelImage.getInstance();
        if (image.Load(bitmap)) {
            switch (compressType) {
                case 0:
                    image.MakeLD(startPosX, startPosY, width, ditheringType, rotation, threshold, 30);
                    break;
                case 1:
                    image.MakeLC(startPosX, startPosY, width, ditheringType, rotation, threshold, 30);
                    break;
                case 2:
                    image.MakeLCL(startPosX, startPosY, width, ditheringType, rotation, threshold, 30);
                    break;
            }
        }
        byte[] PopAll = image.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "image.PopAll()");
        return inputCommand(PopAll);
    }

    public final int drawImageFile(@NotNull String fileName, int startPosX, int startPosY, int width, int threshold, int ditheringType, int compressType, int rotation) {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        LogService.LogI(2, TAG, "++ drawImageFile(" + fileName + ", " + startPosX + ", " + startPosY + ", " + width + ", " + threshold + ", " + ditheringType + ", " + compressType + ", " + rotation + ") ++");
        if (fileName.length() == 0) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        LabelImage image = LabelImage.getInstance();
        if (image.Load(fileName)) {
            switch (compressType) {
                case 0:
                    image.MakeLD(startPosX, startPosY, width, ditheringType, rotation, threshold, 50);
                    break;
                case 1:
                    image.MakeLC(startPosX, startPosY, width, ditheringType, rotation, threshold, 50);
                    break;
                case 2:
                    image.MakeLCL(startPosX, startPosY, width, ditheringType, rotation, threshold, 50);
                    break;
            }
        }
        byte[] PopAll = image.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "image.PopAll()");
        return inputCommand(PopAll);
    }

    public final int drawBase64Image(@NotNull String base64Img, boolean isTransparent, int startPosX, int startPosY, int width, int threshold, int ditheringType, int compressType, int rotation) {
        Intrinsics.checkNotNullParameter(base64Img, "base64Img");
        LogService.LogI(2, TAG, "++ drawBase64Image(" + base64Img + ", " + isTransparent + ", " + startPosX + ", " + startPosY + ", " + width + ", " + threshold + ", " + ditheringType + ", " + compressType + ", " + rotation + ") ++");
        if (base64Img.length() == 0) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        LabelImage image = LabelImage.getInstance();
        if (image.Load(base64Img, isTransparent)) {
            switch (compressType) {
                case 0:
                    image.MakeLD(startPosX, startPosY, width, ditheringType, rotation, threshold, 50);
                    break;
                case 1:
                    image.MakeLC(startPosX, startPosY, width, ditheringType, rotation, threshold, 50);
                    break;
                case 2:
                    image.MakeLCL(startPosX, startPosY, width, ditheringType, rotation, threshold, 50);
                    break;
            }
        }
        byte[] PopAll = image.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "image.PopAll()");
        return inputCommand(PopAll);
    }

    public final int drawBase64Image(@NotNull String base64Img, boolean isTransparent, int startPosX, int startPosY, int width, int threshold, int ditheringType, int compressType, int rotation, int brightness) {
        Intrinsics.checkNotNullParameter(base64Img, "base64Img");
        LogService.LogI(2, TAG, "++ drawBase64Image(" + base64Img + ", " + isTransparent + ", " + startPosX + ", " + startPosY + ", " + width + ", " + threshold + ", " + ditheringType + ", " + compressType + ", " + rotation + ", " + brightness + ") ++");
        if (base64Img.length() == 0) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        LabelImage image = LabelImage.getInstance();
        if (image.Load(base64Img, isTransparent)) {
            switch (compressType) {
                case 0:
                    image.MakeLD(startPosX, startPosY, width, ditheringType, rotation, threshold, brightness);
                    break;
                case 1:
                    image.MakeLC(startPosX, startPosY, width, ditheringType, rotation, threshold, brightness);
                    break;
                case 2:
                    image.MakeLCL(startPosX, startPosY, width, ditheringType, rotation, threshold, brightness);
                    break;
            }
        }
        byte[] PopAll = image.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "image.PopAll()");
        return inputCommand(PopAll);
    }

    public final int setPDFLicenseKey(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        LogService.LogI(2, TAG, "++ setPDFLicenseKey(" + key + ") ++");
        this.isPDFAvailable = true;
        return 0;
    }

    public final int drawPDFFile(@NotNull Uri uri, int horizontalStartPosition, int verticalStartPosition, int page, int width, int level, boolean dithering, boolean compress) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        LogService.LogI(2, TAG, "++ drawPDFFile(" + uri + ", " + horizontalStartPosition + ", " + verticalStartPosition + ", " + page + ", " + width + ", " + level + ", " + dithering + ", " + compress + ") ++");
        Bitmap render = getPdfPage(uri, page);
        if (render == null) {
            LogService.LogI(2, TAG, "render is null");
            return -1;
        } else if (compress) {
            drawCompressionImage(render, horizontalStartPosition, verticalStartPosition, width, level, dithering);
            return 0;
        } else {
            drawBitmap(render, horizontalStartPosition, verticalStartPosition, width, level, dithering);
            return 0;
        }
    }

    public final int drawPDFFile(@NotNull Uri uri, int horizontalStartPosition, int verticalStartPosition, int page, int width, int level, boolean dithering, boolean compress, int rotation, int brightness) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        LogService.LogI(2, TAG, "++ drawPDFFile(" + uri + ", " + horizontalStartPosition + ", " + verticalStartPosition + ", " + page + ", " + width + ", " + level + ", " + dithering + ", " + compress + ") ++");
        Bitmap render = getPdfPage(uri, page);
        if (render == null) {
            LogService.LogI(2, TAG, "render is null");
            return -1;
        } else if (compress) {
            drawCompressionImage(render, horizontalStartPosition, verticalStartPosition, width, level, dithering, rotation, brightness);
            return 0;
        } else {
            drawBitmap(render, horizontalStartPosition, verticalStartPosition, width, level, dithering, rotation, brightness);
            return 0;
        }
    }

    public final void setPdfDpi(int dpi) {
        LogService.LogD(2, TAG, "++ setPdfDpi(" + dpi + ") ++");
        this.pdfDpi = dpi;
    }

    @Nullable
    public final Bitmap getPdfPage(@NotNull Uri uri, int pageNum) {
        ParcelFileDescriptor pfd;
        PdfCore pdfCore;
        Intrinsics.checkNotNullParameter(uri, "uri");
        LogService.LogD(2, TAG, "++ getPdfPage(" + uri + ", " + pageNum + ") ++");
        try {
            pfd = this.context.getContentResolver().openFileDescriptor(uri, "r");
            pdfCore = this.pdfCore;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        if (pdfCore == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pdfCore");
            throw null;
        }
        PdfDocument newDocument = pdfCore.newDocument(pfd, (String) null);
        Intrinsics.checkNotNullExpressionValue(newDocument, "pdfCore.newDocument(pfd, null)");
        this.pdfDoc = newDocument;
        PdfCore pdfCore2 = this.pdfCore;
        if (pdfCore2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pdfCore");
            throw null;
        }
        pdfCore2.setDpi(this.pdfDpi);
        PdfCore pdfCore3 = this.pdfCore;
        if (pdfCore3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pdfCore");
            throw null;
        }
        PdfDocument pdfDocument = this.pdfDoc;
        if (pdfDocument == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pdfDoc");
            throw null;
        }
        Size pageSize = pdfCore3.getPageSize(pdfDocument, pageNum - 1);
        Intrinsics.checkNotNullExpressionValue(pageSize, "pdfCore.getPageSize(pdfDoc, pageNum - 1)");
        this.pageSizeDPI = pageSize;
        Size size = this.pageSizeDPI;
        if (size == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pageSizeDPI");
            throw null;
        }
        int width = size.getWidth();
        Size size2 = this.pageSizeDPI;
        if (size2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pageSizeDPI");
            throw null;
        }
        Bitmap render = Bitmap.createBitmap(width, size2.getHeight(), Bitmap.Config.RGB_565);
        PdfCore pdfCore4 = this.pdfCore;
        if (pdfCore4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pdfCore");
            throw null;
        }
        PdfDocument pdfDocument2 = this.pdfDoc;
        if (pdfDocument2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pdfDoc");
            throw null;
        }
        pdfCore4.openPage(pdfDocument2, pageNum - 1);
        PdfCore pdfCore5 = this.pdfCore;
        if (pdfCore5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pdfCore");
            throw null;
        }
        PdfDocument pdfDocument3 = this.pdfDoc;
        if (pdfDocument3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pdfDoc");
            throw null;
        }
        int i = pageNum - 1;
        Size size3 = this.pageSizeDPI;
        if (size3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pageSizeDPI");
            throw null;
        }
        int width2 = size3.getWidth();
        Size size4 = this.pageSizeDPI;
        if (size4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pageSizeDPI");
            throw null;
        }
        pdfCore5.renderPageBitmap(pdfDocument3, render, i, 0, 0, width2, size4.getHeight());
        return render;
    }

    public final int getCountPdfPages(@NotNull Uri uri) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        if (!this.isPDFAvailable) {
            LogService.LogI(2, TAG, Intrinsics.stringPlus("isPDFAvailable : ", Boolean.valueOf(this.isPDFAvailable)));
            return 0;
        }
        try {
            ParcelFileDescriptor pfd = this.context.getContentResolver().openFileDescriptor(uri, "r");
            PdfCore pdfCore = this.pdfCore;
            if (pdfCore == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pdfCore");
                throw null;
            }
            PdfDocument newDocument = pdfCore.newDocument(pfd, (String) null);
            Intrinsics.checkNotNullExpressionValue(newDocument, "pdfCore.newDocument(pfd, null)");
            this.pdfDoc = newDocument;
            PdfCore pdfCore2 = this.pdfCore;
            if (pdfCore2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pdfCore");
                throw null;
            }
            PdfDocument pdfDocument = this.pdfDoc;
            if (pdfDocument == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pdfDoc");
                throw null;
            }
            int result = pdfCore2.getPageCount(pdfDocument);
            return result;
        } catch (FileNotFoundException e) {
            return -2;
        } catch (IOException e2) {
            return -3;
        } catch (NullPointerException e3) {
            return -4;
        }
    }

    public final int getPdfPageHeight(@NotNull Uri uri, int index) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        if (!this.isPDFAvailable) {
            LogService.LogI(2, TAG, Intrinsics.stringPlus("isPDFAvailable : ", Boolean.valueOf(this.isPDFAvailable)));
            return 0;
        }
        try {
            ParcelFileDescriptor pfd = this.context.getContentResolver().openFileDescriptor(uri, "r");
            PdfCore pdfCore = this.pdfCore;
            if (pdfCore == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pdfCore");
                throw null;
            }
            PdfDocument newDocument = pdfCore.newDocument(pfd, (String) null);
            Intrinsics.checkNotNullExpressionValue(newDocument, "pdfCore.newDocument(pfd, null)");
            this.pdfDoc = newDocument;
            PdfCore pdfCore2 = this.pdfCore;
            if (pdfCore2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pdfCore");
                throw null;
            }
            PdfDocument pdfDocument = this.pdfDoc;
            if (pdfDocument == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pdfDoc");
                throw null;
            }
            int result = pdfCore2.getPageHeight(pdfDocument, index);
            return result;
        } catch (FileNotFoundException e) {
            return -2;
        } catch (IOException e2) {
            return -3;
        } catch (NullPointerException e3) {
            return -4;
        }
    }

    private final int getPDFLicense(String key) {
        LogService.LogI(2, TAG, "++ getPDFLicense(" + key + ") ++");
        if (!isConnected()) {
            return RC_DEVICE_NOT_CONNECTED;
        }
        this.slcsEmul.BufferClear();
        this.slcsEmul.AddBSI_PDF((byte) 1);
        PrinterControl printerControl = this.printerControl;
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        if (printerControl.write(PopAll)) {
            byte[] buffer = this.printerControl.read(1500);
            if (isValidGetPDFLicenseResponse(buffer)) {
                byte[] temp = BXLHelper.Copy(buffer, buffer.length, 1, buffer.length - 2);
                Intrinsics.checkNotNullExpressionValue(temp, "temp");
                String strTemp = new String(temp, Charsets.UTF_8);
                this.isPDFAvailable = Intrinsics.areEqual(strTemp, key);
            } else {
                return 1007;
            }
        }
        return this.isPDFAvailable ? 0 : -1;
    }

    private final boolean isValidGetPDFLicenseResponse(byte[] buffer) {
        LogService.LogI(2, TAG, "++ isValidResponse() ++");
        boolean isValid = buffer.length > 2;
        if (isValid) {
            isValid = buffer[0] == 95 && buffer[buffer.length - 1] == 0;
        }
        return isValid;
    }

    private final boolean setPDFAvailable(byte value) {
        LogService.LogI(2, TAG, "++ setPDFAvailable() ++");
        if (!this.printerControl.isConnected()) {
            return false;
        }
        if (value != 48 && value != 49) {
            return false;
        }
        this.slcsEmul.BufferClear();
        this.slcsEmul.AddBSI_PDF((byte) 4, new byte[]{value}, 1);
        PrinterControl printerControl = this.printerControl;
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return printerControl.write(PopAll);
    }

    private final boolean getPDFAvailable() {
        LogService.LogI(2, TAG, "++ getPDFAvailable() ++");
        if (!this.printerControl.isConnected()) {
            return false;
        }
        this.slcsEmul.BufferClear();
        this.slcsEmul.AddBSI_PDF((byte) 3);
        PrinterControl printerControl = this.printerControl;
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        byte[] read = printerControl.write(PopAll, true, false);
        if (read.length == 1) {
            this.isPDFAvailable = read[0] == 49;
        }
        return this.isPDFAvailable;
    }

    public final int setCharacterSet(int internationalCharacterSet, int codePage) {
        LogService.LogI(2, TAG, "++ setCharacterSet(" + internationalCharacterSet + ", " + codePage + ") ++");
        if (!(0 <= internationalCharacterSet ? internationalCharacterSet <= 15 : false)) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        if (!(0 <= codePage ? codePage <= 22 : false)) {
            Companion companion2 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        setCodePage(codePage);
        this.slcsEmul.AddCharacterSet(internationalCharacterSet, codePage);
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int setPrintingType(int type) {
        LogService.LogI(2, TAG, "++ setPrintingType(" + type + ") ++");
        if (type != 100 && type != 116) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        this.slcsEmul.AddSetPrintingType((byte) type);
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int setMargin(int horizontalMargin, int verticalMargin) {
        LogService.LogI(2, TAG, "++ setMargin(" + horizontalMargin + ", " + verticalMargin + ") ++");
        this.slcsEmul.AddSetMargin(verticalMargin, horizontalMargin);
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int setLeftMarginPosition(int shiftVal) {
        LogService.LogI(2, TAG, "++ testSetLeftMarginPosition(" + shiftVal + ") ++");
        this.slcsEmul.AddSetLeftMargin(shiftVal);
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int initializePrinter() {
        LogService.LogI(2, TAG, "++ initializePrinter() ++");
        return 0;
    }

    public final int printInformation() {
        LogService.LogI(2, TAG, "++ printInformation() ++");
        this.slcsEmul.AddPrintInformation();
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int setBackFeedOption(boolean enabled, int quantity) {
        LogService.LogI(2, TAG, "++ setBackFeedOption(" + enabled + ", " + quantity + ") ++");
        this.slcsEmul.AddSetBackFeed(enabled, quantity);
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int setLength(int labelLength, int gapLength, int mediaType, int offsetLength) {
        LogService.LogI(2, TAG, "++ setLength(" + labelLength + ", " + gapLength + ", " + mediaType + ", " + offsetLength + ") ++");
        int mediaTypeValue = 0;
        switch (mediaType) {
            case 66:
                mediaTypeValue = 2;
                break;
            case 67:
                mediaTypeValue = 1;
                break;
            case 71:
                mediaTypeValue = 0;
                break;
        }
        LogService.LogI(2, TAG, "++ mediaTypeValue : (" + mediaTypeValue + ") ++");
        this.slcsEmul.AddSetPaperLength(labelLength, gapLength, mediaTypeValue, offsetLength);
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int setWidth(int labelWidth) {
        LogService.LogI(2, TAG, "++ setLength(" + labelWidth + ") ++");
        this.slcsEmul.AddSetPaperWidth(labelWidth);
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int setBufferMode(boolean doubleBuffering) {
        LogService.LogI(2, TAG, "++ setBufferMode(" + doubleBuffering + ") ++");
        this.slcsEmul.AddSetBufferMode(doubleBuffering);
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int clearBuffer() {
        LogService.LogI(2, TAG, "++ clearBuffer() ++");
        this.slcsEmul.AddBufferClear();
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int setSpeed(int speed) {
        LogService.LogI(2, TAG, "++ setSpeed(" + speed + ") ++");
        if (!(0 <= speed ? speed <= 6 : false)) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        this.slcsEmul.AddSetSpeed(speed);
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int setDensity(int density) {
        LogService.LogI(2, TAG, "++ setDensity(" + density + ") ++");
        if (density < 0) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        this.slcsEmul.AddSetDensity(density);
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int setOrientation(int orientation) {
        LogService.LogI(2, TAG, "++ setOrientation(" + orientation + ") ++");
        if (orientation != 84 && orientation != 66) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        this.slcsEmul.AddSetOrientation(orientation == 84 ? 0 : 1);
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int setOffset(int offset) {
        LogService.LogI(2, TAG, "++ setOffset(" + offset + ") ++");
        if (!(-100 <= offset ? offset <= 100 : false)) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        this.slcsEmul.AddSetOffset(offset);
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int setCutterPosition(int position) {
        LogService.LogI(2, TAG, "++ setCutterPosition(" + position + ") ++");
        if (!(-100 <= position ? position <= 100 : false)) {
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        }
        this.slcsEmul.AddSetTearOffPosition(position);
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int setAutoCutter(boolean enabled, int cuttingPeriod) {
        LogService.LogI(2, TAG, "++ setAutoCutter(" + enabled + ") ++");
        this.slcsEmul.AddCut(enabled, cuttingPeriod);
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int setRewinder(boolean enabled) {
        LogService.LogI(2, TAG, "++ setRewinder(" + enabled + ") ++");
        if (enabled) {
            this.slcsEmul.AddDirectCommand(REWINDER_ENABLE, true);
        } else {
            this.slcsEmul.AddDirectCommand(REWINDER_DISABLE, true);
        }
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    @NotNull
    public final byte[] getStatus(boolean checkImageBuffer) {
        LogService.LogI(2, TAG, "++ getStatus(" + checkImageBuffer + ") ++");
        this.printerControl.setCurrentProcess(1);
        this.slcsEmul.AddCheckStatus(checkImageBuffer ? 1 : 0);
        PrinterControl printerControl = this.printerControl;
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return printerControl.write(PopAll, true, true);
    }

    public final void getStatus(boolean checkImageBuffer, @NotNull Function1<? super byte[], Unit> function1) {
        Intrinsics.checkNotNullParameter(function1, "readListener");
        LogService.LogI(2, TAG, "++ getStatus(" + checkImageBuffer + ") ++");
        this.slcsEmul.AddCheckStatus(checkImageBuffer ? 1 : 0);
        PrinterControl printerControl = this.printerControl;
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        if (printerControl.write(PopAll)) {
            this.printerControl.read(function1);
        }
    }

    @NotNull
    public final byte[] getPrinterInformation(int param) {
        LogService.LogI(2, TAG, "++ getPrinterInformation(" + param + ") ++");
        this.printerControl.setCurrentProcess(param == 0 ? 4 : 5);
        this.slcsEmul.AddGetInformation(param);
        PrinterControl printerControl = this.printerControl;
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return printerControl.write(PopAll, true, true);
    }

    public final void getPrinterInformation(int param, @NotNull Function1<? super byte[], Unit> function1) {
        Intrinsics.checkNotNullParameter(function1, "readListener");
        LogService.LogI(2, TAG, "++ getPrinterInformation(" + param + ") ++");
        this.slcsEmul.AddGetInformation(param);
        PrinterControl printerControl = this.printerControl;
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        if (printerControl.write(PopAll)) {
            this.printerControl.read(function1);
        }
    }

    public final int print(int set, int copy) {
        LogService.LogI(2, TAG, "++ print(" + set + ',' + copy + ')');
        if (set < 0 || set > 65535) {
            LogService.LogI(2, TAG, Intrinsics.stringPlus("Invalid parameter : ", Integer.valueOf(set)));
            Companion companion = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        } else if (copy < 0 || copy > 65535) {
            LogService.LogI(2, TAG, Intrinsics.stringPlus("Invalid parameter : ", Integer.valueOf(copy)));
            Companion companion2 = Companion;
            LAST_ERROR = RC_INVALID_PARAMETER;
            return RC_INVALID_PARAMETER;
        } else {
            this.slcsEmul.AddPrints(set, copy);
            byte[] PopAll = this.slcsEmul.PopAll();
            Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
            return inputCommand(PopAll);
        }
    }

    public final int getLastError() {
        return LAST_ERROR;
    }

    public final void beginTransactionPrint() {
        Companion companion = Companion;
        transactionPrintMode = true;
    }

    public final int endTransactionPrint() {
        Companion companion = Companion;
        transactionPrintMode = false;
        this.printerControl.setCurrentProcess(3);
        this.slcsEmul.AddGetInformation(2);
        PrinterControl printerControl = this.printerControl;
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        if (printerControl.write(PopAll)) {
            if (!(this.printerControl.read(true).length == 0)) {
                return 3;
            }
            return -1;
        }
        return -1;
    }

    public final void executeDirectIo(@NotNull String command, boolean hasResponse, int responseLength) {
        Intrinsics.checkNotNullParameter(command, "command");
        LogService.LogI(2, TAG, "++ executeDirectIo(" + command + ", " + hasResponse + ", " + responseLength + ") ++");
        this.printerControl.setCurrentProcess(2);
        this.slcsEmul.AddDirectCommand(command, true);
        PrinterControl printerControl = this.printerControl;
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        printerControl.write(PopAll, hasResponse, true);
    }

    public final void executeDirectIo(@NotNull String command, @NotNull Function1<? super byte[], Unit> function1) {
        Intrinsics.checkNotNullParameter(command, "command");
        Intrinsics.checkNotNullParameter(function1, "readListener");
        LogService.LogI(2, TAG, "++ executeDirectIo(" + command + ") ++");
        this.slcsEmul.AddDirectCommand(command, true);
        PrinterControl printerControl = this.printerControl;
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        if (printerControl.write(PopAll)) {
            this.printerControl.read(function1);
        }
    }

    public final void executeDirectIo(@NotNull byte[] command, boolean hasResponse, int responseLength) {
        Intrinsics.checkNotNullParameter(command, "command");
        LogService.LogI(2, TAG, "++ executeDirectIo(" + command + ", " + hasResponse + ", " + responseLength + ") ++");
        this.printerControl.setCurrentProcess(2);
        ByteBuffer buffer = ByteBuffer.allocate(command.length + "\r\n".length());
        buffer.put(command);
        byte[] bytes = "\r\n".getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "(this as java.lang.String).getBytes(charset)");
        buffer.put(bytes);
        PrinterControl printerControl = this.printerControl;
        byte[] array = buffer.array();
        Intrinsics.checkNotNullExpressionValue(array, "buffer.array()");
        printerControl.write(array, hasResponse, true);
    }

    public final void executeDirectIo(@NotNull byte[] command, @NotNull Function1<? super byte[], Unit> function1) {
        Intrinsics.checkNotNullParameter(command, "command");
        Intrinsics.checkNotNullParameter(function1, "readListener");
        LogService.LogI(2, TAG, "++ executeDirectIo(" + command + ") ++");
        ByteBuffer buffer = ByteBuffer.allocate(command.length + "\r\n".length());
        buffer.put(command);
        byte[] bytes = "\r\n".getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "(this as java.lang.String).getBytes(charset)");
        buffer.put(bytes);
        PrinterControl printerControl = this.printerControl;
        byte[] array = buffer.array();
        Intrinsics.checkNotNullExpressionValue(array, "buffer.array()");
        if (printerControl.write(array)) {
            this.printerControl.read(function1);
        }
    }

    public final void executeDirectIoWithoutCRLF(@NotNull byte[] command) {
        Intrinsics.checkNotNullParameter(command, "command");
        this.printerControl.write(command);
    }

    public final int setupRFID(int rfidType, int numberOfRetries, int numberOfLabel, int radioPower) {
        LogService.LogI(2, TAG, "++ setupRFID(" + rfidType + ", " + numberOfRetries + ", " + numberOfLabel + ", " + radioPower + ") ++");
        this.slcsEmul.AddSetupRFID(rfidType, numberOfRetries, numberOfLabel, radioPower);
        if (transactionPrintMode) {
            return 0;
        }
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int calibrateRFID() {
        LogService.LogI(2, TAG, "++ calibrateRFID()++");
        this.slcsEmul.AddCalibrateRFID();
        if (transactionPrintMode) {
            return 0;
        }
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int setRFIDPosition(int transPosition) {
        LogService.LogI(2, TAG, "++ setRFIDPosition(" + transPosition + ")++");
        if (this.printerControl.getMinRFIDPosition() <= transPosition && this.printerControl.getMaxRFIDPosition() >= transPosition) {
            this.slcsEmul.AddSetRFIDPosition(transPosition);
            if (!transactionPrintMode) {
                PrinterControl printerControl = this.printerControl;
                byte[] PopAll = this.slcsEmul.PopAll();
                Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
                if (printerControl.write(PopAll)) {
                    return 0;
                }
                return -1;
            }
            return 0;
        }
        return -1;
    }

    public final int setEPCDataStructure(int totalSize, @NotNull String fieldSizes) {
        Intrinsics.checkNotNullParameter(fieldSizes, "fieldSizes");
        LogService.LogI(2, TAG, "++ setEPCDataStructure(" + totalSize + ", " + fieldSizes + ") ++");
        this.slcsEmul.AddSetEPCDataStructure(totalSize, fieldSizes);
        if (transactionPrintMode) {
            return 0;
        }
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int writeRFID(int dataType, int startingBlockNumber, int dataLength, @NotNull String data) {
        Intrinsics.checkNotNullParameter(data, "data");
        LogService.LogI(2, TAG, "++ writeRFID(" + dataType + ", " + startingBlockNumber + ", " + dataLength + ", " + data + ")++");
        this.slcsEmul.AddWriteRFID(dataType, startingBlockNumber, dataLength, data);
        if (transactionPrintMode) {
            return 0;
        }
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int setRFIDPassword(@NotNull String oldAccessPwd, @NotNull String oldKillPwd, @NotNull String newAccessPwd, @NotNull String newKillPwd) {
        Intrinsics.checkNotNullParameter(oldAccessPwd, "oldAccessPwd");
        Intrinsics.checkNotNullParameter(oldKillPwd, "oldKillPwd");
        Intrinsics.checkNotNullParameter(newAccessPwd, "newAccessPwd");
        Intrinsics.checkNotNullParameter(newKillPwd, "newKillPwd");
        LogService.LogI(2, TAG, "++ setRFIDPassword(" + oldAccessPwd + ", " + oldKillPwd + ", " + newAccessPwd + ", " + newKillPwd + ")++");
        this.slcsEmul.AddSetRFIDPassword(oldAccessPwd, oldKillPwd, newAccessPwd, newKillPwd);
        if (transactionPrintMode) {
            return 0;
        }
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int lockRFID() {
        LogService.LogI(2, TAG, "++ lockRFID() ++");
        this.slcsEmul.AddLockRFID();
        if (transactionPrintMode) {
            return 0;
        }
        byte[] PopAll = this.slcsEmul.PopAll();
        Intrinsics.checkNotNullExpressionValue(PopAll, "slcsEmul.PopAll()");
        return inputCommand(PopAll);
    }

    public final int getPrinterDpi() {
        return this.printerControl.getConnectedPrinterDpi();
    }

    public final void setTransferFileListener(@NotNull TransferFileListener transferFileListener) {
        Intrinsics.checkNotNullParameter(transferFileListener, "transferFileListener");
        this.transferFileListener = transferFileListener;
    }

    public final int transferFile(@NotNull String filePath) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        LogService.LogI(2, TAG, "++ transferFile(" + filePath + ")++");
        if (!isConnected()) {
            TransferFileListener transferFileListener = this.transferFileListener;
            if (transferFileListener != null) {
                transferFileListener.onFailed(RC_DEVICE_NOT_CONNECTED);
            }
            Companion companion = Companion;
            LAST_ERROR = RC_DEVICE_NOT_CONNECTED;
            return RC_DEVICE_NOT_CONNECTED;
        } else if (!BXLFileHelper.Exist(filePath)) {
            TransferFileListener transferFileListener2 = this.transferFileListener;
            if (transferFileListener2 != null) {
                transferFileListener2.onFailed(-2);
            }
            Companion companion2 = Companion;
            LAST_ERROR = -2;
            return -2;
        } else {
            byte[] readByteArray = BXLFileHelper.ReadAll(filePath);
            if (readByteArray != null) {
                if (!(readByteArray.length == 0)) {
                    if (this.printerControl.write(readByteArray)) {
                        TransferFileListener transferFileListener3 = this.transferFileListener;
                        if (transferFileListener3 == null) {
                            return 0;
                        }
                        transferFileListener3.onSuccess();
                        return 0;
                    }
                    TransferFileListener transferFileListener4 = this.transferFileListener;
                    if (transferFileListener4 != null) {
                        transferFileListener4.onFailed(-1);
                    }
                    Companion companion3 = Companion;
                    LAST_ERROR = -1;
                    return -1;
                }
            }
            TransferFileListener transferFileListener5 = this.transferFileListener;
            if (transferFileListener5 != null) {
                transferFileListener5.onFailed(-1);
            }
            Companion companion4 = Companion;
            LAST_ERROR = -1;
            return -1;
        }
    }

    public final int transferFile(@NotNull String filePath, @NotNull Function1<? super Integer, Unit> function1) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        Intrinsics.checkNotNullParameter(function1, "transferFileListener");
        LogService.LogI(2, TAG, "++ transferFile(" + filePath + ")++");
        if (!isConnected()) {
            function1.invoke(Integer.valueOf((int) RC_DEVICE_NOT_CONNECTED));
            TransferFileListener transferFileListener = this.transferFileListener;
            if (transferFileListener != null) {
                transferFileListener.onFailed(RC_DEVICE_NOT_CONNECTED);
            }
            Companion companion = Companion;
            LAST_ERROR = RC_DEVICE_NOT_CONNECTED;
            return RC_DEVICE_NOT_CONNECTED;
        } else if (!BXLFileHelper.Exist(filePath)) {
            function1.invoke(-2);
            TransferFileListener transferFileListener2 = this.transferFileListener;
            if (transferFileListener2 != null) {
                transferFileListener2.onFailed(-2);
            }
            Companion companion2 = Companion;
            LAST_ERROR = -2;
            return -2;
        } else {
            byte[] readByteArray = BXLFileHelper.ReadAll(filePath);
            if (readByteArray != null) {
                if (!(readByteArray.length == 0)) {
                    if (this.printerControl.write(readByteArray)) {
                        function1.invoke(0);
                        TransferFileListener transferFileListener3 = this.transferFileListener;
                        if (transferFileListener3 == null) {
                            return 0;
                        }
                        transferFileListener3.onSuccess();
                        return 0;
                    }
                    function1.invoke(-1);
                    TransferFileListener transferFileListener4 = this.transferFileListener;
                    if (transferFileListener4 != null) {
                        transferFileListener4.onFailed(-1);
                    }
                    Companion companion3 = Companion;
                    LAST_ERROR = -1;
                    return -1;
                }
            }
            function1.invoke(-1);
            TransferFileListener transferFileListener5 = this.transferFileListener;
            if (transferFileListener5 != null) {
                transferFileListener5.onFailed(-1);
            }
            Companion companion4 = Companion;
            LAST_ERROR = -1;
            return -1;
        }
    }

    /* compiled from: BixolonLabelPrinter.kt */
    @Metadata(mv = {1, 5, 1}, k = 1, xi = 48, d1 = {"��5\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b*\n\u0002\u0010\f\n\u0002\b(\n\u0002\u0010\u000e\n\u0003\b\u009a\u0001\n\u0002\u0010\u0005\n\u0002\b\u001d\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\r\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u0013\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u0014\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u0015\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u0016\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u0017\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u0018\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u0019\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u001a\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u001b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u001c\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u001d\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u001e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u001f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010 \u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010!\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\"\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010#\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010$\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010%\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010&\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010'\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010(\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010)\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010*\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010+\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010,\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010-\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010.\u001a\u00020/X\u0086T¢\u0006\u0002\n��R\u000e\u00100\u001a\u00020/X\u0086T¢\u0006\u0002\n��R\u000e\u00101\u001a\u00020/X\u0086T¢\u0006\u0002\n��R\u000e\u00102\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u00103\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u00104\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u00105\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u00106\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u00107\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u00108\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u00109\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010:\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010;\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010<\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010=\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010>\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010?\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010@\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010A\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010B\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010C\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010D\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010E\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010F\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010G\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010H\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010I\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010J\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010K\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010L\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010M\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010N\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010O\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010P\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010Q\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010R\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010S\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010T\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010U\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010V\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010W\u001a\u00020XX\u0086T¢\u0006\u0002\n��R\u000e\u0010Y\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010Z\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010[\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\\\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010]\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010^\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010_\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010`\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010a\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010c\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010d\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010g\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010h\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010i\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010j\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010k\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010l\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010m\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010o\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010p\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010q\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010r\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010s\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010u\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010v\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010w\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010x\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010y\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010z\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010{\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010|\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010}\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010~\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u007f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u0080\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u0081\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u0082\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u0083\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u0084\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u0085\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u0086\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u0087\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u0088\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u0089\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u008a\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u008b\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u008c\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u008d\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u008e\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u008f\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u0090\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u0091\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u0092\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u0093\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u0094\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u0095\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u0096\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u0097\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u001f\u0010\u0098\u0001\u001a\u00020\u0004X\u0086\u000e¢\u0006\u0012\n��\u001a\u0006\b\u0099\u0001\u0010\u009a\u0001\"\u0006\b\u009b\u0001\u0010\u009c\u0001R\u000f\u0010\u009d\u0001\u001a\u00020XX\u0086T¢\u0006\u0002\n��R\u000f\u0010\u009e\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u009f\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010 \u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010¡\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010¢\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010£\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010¤\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010¥\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010¦\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010§\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010¨\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010©\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010ª\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010«\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010¬\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u00ad\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010®\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010¯\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010°\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010±\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010²\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010³\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010´\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010µ\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010¶\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010·\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010¸\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010¹\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010º\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010»\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010¼\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010½\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010¾\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010¿\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010À\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010Á\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010Â\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010Ã\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010Ä\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010Å\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010Æ\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010Ç\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010È\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010É\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010Ê\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010Ë\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010Ì\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010Í\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010Î\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010Ï\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010Ð\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010Ñ\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010Ò\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010Ó\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010Ô\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010Õ\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010Ö\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010×\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010Ø\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010Ù\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010Ú\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010Û\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010Ü\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010Ý\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010Þ\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010ß\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010à\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010á\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010â\u0001\u001a\u00020XX\u0086T¢\u0006\u0002\n��R\u000f\u0010ã\u0001\u001a\u00020XX\u0086T¢\u0006\u0002\n��R\u000f\u0010ä\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010å\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010æ\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010ç\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010è\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010é\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010ê\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010ë\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010ì\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010í\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010î\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010ï\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010ð\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010ñ\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u0010\u0010ò\u0001\u001a\u00030ó\u0001X\u0086T¢\u0006\u0002\n��R\u0010\u0010ô\u0001\u001a\u00030ó\u0001X\u0086T¢\u0006\u0002\n��R\u0010\u0010õ\u0001\u001a\u00030ó\u0001X\u0086T¢\u0006\u0002\n��R\u0010\u0010ö\u0001\u001a\u00030ó\u0001X\u0086T¢\u0006\u0002\n��R\u0010\u0010÷\u0001\u001a\u00030ó\u0001X\u0086T¢\u0006\u0002\n��R\u0010\u0010ø\u0001\u001a\u00030ó\u0001X\u0086T¢\u0006\u0002\n��R\u0010\u0010ù\u0001\u001a\u00030ó\u0001X\u0086T¢\u0006\u0002\n��R\u0010\u0010ú\u0001\u001a\u00030ó\u0001X\u0086T¢\u0006\u0002\n��R\u0010\u0010û\u0001\u001a\u00030ó\u0001X\u0086T¢\u0006\u0002\n��R\u000f\u0010ü\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010ý\u0001\u001a\u00020XX\u0086T¢\u0006\u0002\n��R\u000f\u0010þ\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010ÿ\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u0080\u0002\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u0081\u0002\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u0082\u0002\u001a\u00020XX\u0086T¢\u0006\u0002\n��R\u000f\u0010\u0083\u0002\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u0084\u0002\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u0085\u0002\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u0086\u0002\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u0087\u0002\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u0088\u0002\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u0089\u0002\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u008a\u0002\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u008b\u0002\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u008c\u0002\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u008d\u0002\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u008e\u0002\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000f\u0010\u008f\u0002\u001a\u00020XX\u0086T¢\u0006\u0002\n��R \u0010\u0090\u0002\u001a\u00030\u0091\u0002X\u0086\u000e¢\u0006\u0012\n��\u001a\u0006\b\u0092\u0002\u0010\u0093\u0002\"\u0006\b\u0094\u0002\u0010\u0095\u0002¨\u0006\u0096\u0002"}, d2 = {"Lcom/bixolon/labelprinter/BixolonLabelPrinter$Companion;", "", "()V", "BARCODE_CODABAR", "", "BARCODE_CODE11", "BARCODE_CODE128", "BARCODE_CODE39", "BARCODE_CODE93", "BARCODE_EAN13", "BARCODE_EAN8", "BARCODE_I2OF5", "BARCODE_INDUSTRIAL_2OF5", "BARCODE_LOGMARS", "BARCODE_ORIGIN_POINT_CENTER", "BARCODE_ORIGIN_POINT_UPPER_LEFT", "BARCODE_PLANET", "BARCODE_POSTNET", "BARCODE_STANDARD_2OF5", "BARCODE_TYPE_RSS14", "BARCODE_TYPE_RSS14_STACKED", "BARCODE_TYPE_RSS14_STACKED_OMNIDIRECTIONAL", "BARCODE_TYPE_RSS14_TRUNCATED", "BARCODE_TYPE_RSS_EAN13", "BARCODE_TYPE_RSS_EAN8", "BARCODE_TYPE_RSS_EXPANDED", "BARCODE_TYPE_RSS_LIMITIED", "BARCODE_TYPE_RSS_UCC_EAN128_CCAB", "BARCODE_TYPE_RSS_UCC_EAN128_CCC", "BARCODE_TYPE_RSS_UPCA", "BARCODE_TYPE_RSS_UPCE", "BARCODE_UCC_EAN128", "BARCODE_UPC_A", "BARCODE_UPC_E", "BARCODE_UPC_EAN_EXTENSIONS", "BLOCK_OPTION_BOX", "BLOCK_OPTION_LINE_DELETE", "BLOCK_OPTION_LINE_EXCLUSIVE_OR", "BLOCK_OPTION_LINE_OVERWRITING", "BLOCK_OPTION_SLOPE", "CIRCLE_SIZE_DIAMETER11", "CIRCLE_SIZE_DIAMETER13", "CIRCLE_SIZE_DIAMETER21", "CIRCLE_SIZE_DIAMETER5", "CIRCLE_SIZE_DIAMETER7", "CIRCLE_SIZE_DIAMETER9", "CODABLOCK_MODE_A", "", "CODABLOCK_MODE_E", "CODABLOCK_MODE_F", "CODE49_HRI_ABOVE_BARCODE", "CODE49_HRI_BELOW_BARCODE", "CODE49_HRI_NOT_PRINTED", "CODE49_STRING_MODE_AUTOMATIC_MODE", "CODE49_STRING_MODE_GROUP_ALPHANUMERIC", "CODE49_STRING_MODE_MULTIPLE_READ_ALPHANUMERIC", "CODE49_STRING_MODE_REGULAR_ALPHANUMERIC", "CODE49_STRING_MODE_REGULAR_ALPHANUMERIC_SHIFT1", "CODE49_STRING_MODE_REGULAR_ALPHANUMERIC_SHIFT2", "CODE49_STRING_MODE_REGULAR_NUMERIC", "CODEPAGE", "CODE_PAGE_CP437_USA", "CODE_PAGE_CP737_GREEK", "CODE_PAGE_CP775_BALTIC", "CODE_PAGE_CP850_LATIN1", "CODE_PAGE_CP852_LATIN2", "CODE_PAGE_CP855_CYRILLIC", "CODE_PAGE_CP857_TURKISH", "CODE_PAGE_CP858_LATIN1_EURO", "CODE_PAGE_CP860_PORTUGUESE", "CODE_PAGE_CP862_HEBREW", "CODE_PAGE_CP863_CANADIAN_FRENCH", "CODE_PAGE_CP864_ARABIC", "CODE_PAGE_CP865_NORDIC", "CODE_PAGE_CP865_WCP1252_EUROPEAN_COMBINED", "CODE_PAGE_CP866_CYRILLIC", "CODE_PAGE_CP928_GREEK", "CODE_PAGE_WCP1250_LATIN2", "CODE_PAGE_WCP1251_CYRILLIC", "CODE_PAGE_WCP1252_LATIN1", "CODE_PAGE_WCP1253_GREEK", "CODE_PAGE_WCP1254_TURKISH", "CODE_PAGE_WCP1255_HEBREW", "CODE_PAGE_WCP1257_BALTIC", "DATA_COMPRESSION_BINARY", "DATA_COMPRESSION_NUMERIC", "DATA_COMPRESSION_TEXT", "DEVICE_NAME", "", "ECC_LEVEL_15", "ECC_LEVEL_25", "ECC_LEVEL_30", "ECC_LEVEL_7", "ERROR_CODE_FILE_NOT_FOUND", "ERROR_CODE_I_O", "ERROR_CODE_PRINTER_NOT_SUPPORTED", "ERROR_PDF_RENDER_IS_NULL", "FONT_SIZE_10", "FONT_SIZE_12", "FONT_SIZE_14", "FONT_SIZE_15", "FONT_SIZE_18", "FONT_SIZE_20", "FONT_SIZE_24", "FONT_SIZE_30", "FONT_SIZE_6", "FONT_SIZE_8", "FONT_SIZE_BIG5", "FONT_SIZE_GB2312", "FONT_SIZE_KOREAN1", "FONT_SIZE_KOREAN2", "FONT_SIZE_KOREAN3", "FONT_SIZE_KOREAN4", "FONT_SIZE_KOREAN5", "FONT_SIZE_KOREAN6", "FONT_SIZE_SHIFT_JIS", "HRI_ABOVE_BARCODE", "HRI_ABOVE_FONT_SIZE_1", "HRI_ABOVE_FONT_SIZE_2", "HRI_ABOVE_FONT_SIZE_3", "HRI_ABOVE_FONT_SIZE_4", "HRI_BELOW_BARCODE", "HRI_BELOW_FONT_SIZE_1", "HRI_BELOW_FONT_SIZE_2", "HRI_BELOW_FONT_SIZE_3", "HRI_BELOW_FONT_SIZE_4", "HRI_NOT_PRINT", "HRI_NOT_PRINTED", "INTERFACE_TYPE_BLUETOOTH", "INTERFACE_TYPE_BLUETOOTH_LOW_ENERGY", "INTERFACE_TYPE_NETWORK", "INTERFACE_TYPE_USB", "INTERFACE_TYPE_WIFI_DIRECT", "INTERNATIONAL_CHARACTER_SET_CHINA", "INTERNATIONAL_CHARACTER_SET_DENMARK1", "INTERNATIONAL_CHARACTER_SET_DENMARK2", "INTERNATIONAL_CHARACTER_SET_FRANCE", "INTERNATIONAL_CHARACTER_SET_GERMANY", "INTERNATIONAL_CHARACTER_SET_ITALY", "INTERNATIONAL_CHARACTER_SET_JAPAN", "INTERNATIONAL_CHARACTER_SET_KOREA", "INTERNATIONAL_CHARACTER_SET_LATIN_AMERICA", "INTERNATIONAL_CHARACTER_SET_NORWAY", "INTERNATIONAL_CHARACTER_SET_SLOVENIA_CROATIA", "INTERNATIONAL_CHARACTER_SET_SPAIN1", "INTERNATIONAL_CHARACTER_SET_SPAIN2", "INTERNATIONAL_CHARACTER_SET_SWEDEN", "INTERNATIONAL_CHARACTER_SET_UK", "INTERNATIONAL_CHARACTER_SET_USA", "LABEL_IMAGE_LZMA", "LABEL_IMAGE_NONE", "LABEL_IMAGE_RLE", "LAST_ERROR", "getLAST_ERROR", "()I", "setLAST_ERROR", "(I)V", "LOG", "MAXICODE_MODE0", "MAXICODE_MODE2", "MAXICODE_MODE3", "MAXICODE_MODE4", "MEDIA_TYPE_BLACK_MARK", "MEDIA_TYPE_CONTINUOUS", "MEDIA_TYPE_GAP", "MESSAGE_BLUETOOTH_DEVICE_SET", "MESSAGE_DEVICE_NAME", "MESSAGE_LOG", "MESSAGE_NETWORK_DEVICE_SET", "MESSAGE_OUTPUT_COMPLETE", "MESSAGE_READ", "MESSAGE_STATE_CHANGE", "MESSAGE_TOAST", "MESSAGE_USB_DEVICE_SET", "MSI_BARCODE_CHECKDIGIT_1MOD10", "MSI_BARCODE_CHECKDIGIT_1MOD11_AND_1MOD_10", "MSI_BARCODE_CHECKDIGIT_2MOD10", "MSI_BARCODE_CHECKDIGIT_NONE", "MSI_BARCODE_HRI_ABOVE_BARCODE", "MSI_BARCODE_HRI_BELOW_BARCODE", "MSI_BARCODE_HRI_NOT_PRINTED", "ORIENTATION_BOTTOM_TO_TOP", "ORIENTATION_TOP_TO_BOTTOM", "PDF417_ERROR_CORRECTION_LEVEL0", "PDF417_ERROR_CORRECTION_LEVEL1", "PDF417_ERROR_CORRECTION_LEVEL2", "PDF417_ERROR_CORRECTION_LEVEL3", "PDF417_ERROR_CORRECTION_LEVEL4", "PDF417_ERROR_CORRECTION_LEVEL5", "PDF417_ERROR_CORRECTION_LEVEL6", "PDF417_ERROR_CORRECTION_LEVEL7", "PDF417_ERROR_CORRECTION_LEVEL8", "PDF417_HRI_BELOW_BARCODE", "PDF417_HRI_NOT_PRINTED", "PLESSEY_BARCODE_HRI_ABOVE_BARCODE", "PLESSEY_BARCODE_HRI_BELOW_BARCODE", "PLESSEY_BARCODE_HRI_NOT_PRINTED", "PRINTER_INFORMATION_FIRMWARE_VERSION", "PRINTER_INFORMATION_MODEL_NAME", "PRINTER_INFORMATION_SERIAL_NUMBER", "PRINTING_TYPE_DIRECT_THERMAL", "PRINTING_TYPE_THERMAL_TRANSFER", "PROCESS_EXECUTE_DIRECT_IO", "PROCESS_GET_INFORMATION_FIRMWARE_VERSION", "PROCESS_GET_INFORMATION_MODEL_NAME", "PROCESS_GET_INFORMATION_SERIAL_NUMBER", "PROCESS_GET_STATUS", "PROCESS_NONE", "PROCESS_OUTPUT_COMPLETE", "QR_CODE_MODEL1", "QR_CODE_MODEL2", "RC_BT_NOT_AVAILABLE", "RC_BT_NOT_ENABLED", "RC_CONNECTION_TIMEOUT", "RC_DEVICE_ALREADY_CONNECTED", "RC_DEVICE_CONNECTION_FAIL", "RC_DEVICE_CONNECTION_LOSS", "RC_DEVICE_NOT_CONNECTED", "RC_FAIL", "RC_FAIL_READ_PORT", "RC_FAIL_WRITE_PORT", "RC_INVALID_PARAMETER", "RC_NOT_SUPPORTED", "RC_SOCKET_ERROR", "RC_SUCCESS", "RC_USB_NOT_AVAILABLE", "REWINDER_DISABLE", "REWINDER_ENABLE", "ROTATION_180_DEGREES", "ROTATION_270_DEGREES", "ROTATION_90_DEGREES", "ROTATION_NONE", "SPEED_25IPS", "SPEED_30IPS", "SPEED_40IPS", "SPEED_50IPS", "SPEED_60IPS", "SPEED_70IPS", "SPEED_80IPS", "STATE_CONNECTED", "STATE_CONNECTING", "STATE_NONE", "STATUS_1ST_BYTE_AUTO_SENSING_FAILURE", "", "STATUS_1ST_BYTE_COVER_OPEN", "STATUS_1ST_BYTE_CUTTER_JAMMED", "STATUS_1ST_BYTE_PAPER_EMPTY", "STATUS_1ST_BYTE_RIBBON_END_ERROR", "STATUS_1ST_BYTE_TPH_OVERHEAT", "STATUS_2ND_BYTE_BUILDING_IN_IMAGE_BUFFER", "STATUS_2ND_BYTE_PAUSED_IN_PEELER_UNIT", "STATUS_2ND_BYTE_PRINTING_IN_IMAGE_BUFFER", "STATUS_NORMAL", "TAG", "TEXT_ALIGNMENT_LEFT", "TEXT_ALIGNMENT_NONE", "TEXT_ALIGNMENT_RIGHT", "TEXT_ALIGNMENT_RIGHT_TO_LEFT", "TOAST", "VECTOR_FONT_ASCII", "VECTOR_FONT_BIG5", "VECTOR_FONT_GB2312", "VECTOR_FONT_KS5601", "VECTOR_FONT_OCR_A", "VECTOR_FONT_OCR_B", "VECTOR_FONT_SHIFT_JIS", "VECTOR_FONT_TEXT_ALIGNMENT_CENTER", "VECTOR_FONT_TEXT_ALIGNMENT_LEFT", "VECTOR_FONT_TEXT_ALIGNMENT_RIGHT", "VECTOR_FONT_TEXT_DIRECTION_LEFT_TO_RIGHT", "VECTOR_FONT_TEXT_DIRECTION_RIGHT_TO_LEFT", "VERSION", "transactionPrintMode", "", "getTransactionPrintMode", "()Z", "setTransactionPrintMode", "(Z)V", "BixolonLabelSDK_release"})
    /* loaded from: BixolonLabelPrinterLibrary_V2.0.4.jar:com/bixolon/labelprinter/BixolonLabelPrinter$Companion.class */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }

        public final int getLAST_ERROR() {
            return BixolonLabelPrinter.LAST_ERROR;
        }

        public final void setLAST_ERROR(int i) {
            BixolonLabelPrinter.LAST_ERROR = i;
        }

        public final boolean getTransactionPrintMode() {
            return BixolonLabelPrinter.transactionPrintMode;
        }

        public final void setTransactionPrintMode(boolean z) {
            BixolonLabelPrinter.transactionPrintMode = z;
        }
    }

    private final int convertInternalCodePage(int codePage) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        if (codePage == 0) {
            return 437;
        }
        if (codePage == 1) {
            return 850;
        }
        if (codePage == 2) {
            return 852;
        }
        if (codePage == 3) {
            return 860;
        }
        if (codePage == 4) {
            return 863;
        }
        if (codePage == 5) {
            return 865;
        }
        if (codePage == 6) {
            return 1252;
        }
        if (codePage == 7) {
            return 8651252;
        }
        if (codePage == 8) {
            return 857;
        }
        if (codePage == 9) {
            return 737;
        }
        if (codePage == 10) {
            return 1250;
        }
        if (codePage == 11) {
            return 1253;
        }
        if (codePage == 12) {
            return 1254;
        }
        if (codePage == 13) {
            return 855;
        }
        if (codePage == 14) {
            return 862;
        }
        if (codePage == 15) {
            return 866;
        }
        if (codePage == 16) {
            return 1251;
        }
        if (codePage == 17) {
            return 1255;
        }
        if (codePage == 18) {
            return 928;
        }
        if (codePage == 19) {
            return 864;
        }
        if (codePage == 20) {
            return 775;
        }
        if (codePage == 21) {
            return 1257;
        }
        if (codePage == 22) {
            return 858;
        }
        if (97 <= codePage) {
            z = codePage <= 102;
        } else {
            z = false;
        }
        if (z) {
            z2 = true;
        } else {
            z2 = codePage == 75;
        }
        if (z2) {
            return 949;
        }
        if (codePage == 110) {
            z3 = true;
        } else {
            z3 = codePage == 66;
        }
        if (z3) {
            return 950;
        }
        if (codePage == 109) {
            z4 = true;
        } else {
            z4 = codePage == 71;
        }
        if (z4) {
            return 936;
        }
        if (codePage == 106) {
            z5 = true;
        } else {
            z5 = codePage == 74;
        }
        return z5 ? 932 : 437;
    }

    public final void changeHandler(@NotNull Handler handler) {
        Intrinsics.checkNotNullParameter(handler, "handler");
        this.uiHandler = handler;
    }

    public final void setCodePage(int codePage) {
        Companion companion = Companion;
        CODEPAGE = codePage;
    }

    private final int getCodePage() {
        return CODEPAGE;
    }

    private final int inputCommand(byte[] command) {
        LogService.LogD(2, TAG, "++ sendCommand ++ / transactionPrintMode : " + transactionPrintMode + "++");
        if (transactionPrintMode) {
            this.bxlQueue.pushBack(command);
        }
        if (this.printerControl.write(command)) {
            return 0;
        }
        Companion companion = Companion;
        LAST_ERROR = 50;
        return 50;
    }
}