package org.dasein.cloud.container;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Stas Maksimov (stas.maksimov@software.dell.com)
 * @since 2015.09
 */
public class Scheduler {
    private @Nonnull String name;
    private @Nonnull String providerSchedulerId;
    private @Nonnegative int desiredTasks;
    private @Nonnegative int pendingTasks;
    private @Nonnegative int runningTasks;
    private @Nullable String providerClusterId;
    private @Nullable String taskDefinition;

}
