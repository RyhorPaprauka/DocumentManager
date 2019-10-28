package by.itacademy.documentmanager.config;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DropBoxConfig {

    private static final String ACCESS_TOKEN = "WopTJHwC14AAAAAAAAABYtxDMuNcdXzQj-h7M5m2nyQxLj8nT6N3nOQRcOlFGrH0";

    public DbxClientV2 dropBoxClient() {
        DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial");
        return new DbxClientV2(config, ACCESS_TOKEN);
    }
}
