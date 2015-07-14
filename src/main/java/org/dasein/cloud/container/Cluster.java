package org.dasein.cloud.container;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Stas Maksimov (stas.maksimov@software.dell.com)
 * @since 2015.09
 */
public class Cluster {
    private @Nullable String name;
    private @Nonnull String providerClusterId;

    public Cluster(@Nonnull String providerClusterId, @Nullable String name) {
        this.providerClusterId = providerClusterId;
        this.name = name;
    }

    public @Nullable String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    public @Nonnull String getProviderClusterId() {
        return providerClusterId;
    }

    public void setProviderClusterId(@Nonnull String providerClusterId) {
        this.providerClusterId = providerClusterId;
    }
}
