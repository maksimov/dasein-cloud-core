package org.dasein.cloud.container;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Stas Maksimov (stas.maksimov@software.dell.com)
 * @since 2015.09
 */
public class SchedulerCreateOptions {
    private @Nullable String providerClusterId;
    private @Nonnegative int desiredTasks;
    private @Nonnull String name;
    private @Nullable String taskDefinition;

    private SchedulerCreateOptions(@Nonnull String name) {
        this.name = name;
    }

    public static SchedulerCreateOptions getInstance(@Nonnull String name) {
        return new SchedulerCreateOptions(name);
    }

    public SchedulerCreateOptions withDesiredTasks(@Nonnegative int desiredTasks) {
        this.desiredTasks = desiredTasks;
        return this;
    }

    public SchedulerCreateOptions withProviderClusterId(@Nullable String providerClusterId) {
        this.providerClusterId = providerClusterId;
        return this;
    }

    public SchedulerCreateOptions withTaskDefinition(@Nullable String taskDefinition) {
        this.taskDefinition = taskDefinition;
        return this;
    }

    public @Nullable String getProviderClusterId() {
        return providerClusterId;
    }

    public @Nonnegative int getDesiredTasks() {
        return desiredTasks;
    }

    public @Nonnull String getName() {
        return name;
    }

    public @Nullable String getTaskDefinition() {
        return taskDefinition;
    }
}
