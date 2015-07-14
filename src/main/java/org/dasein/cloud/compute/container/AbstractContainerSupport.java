package org.dasein.cloud.compute.container;

import org.dasein.cloud.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Stas Maksimov (stas.maksimov@software.dell.com)
 * @since 2015.09
 */
public abstract class AbstractContainerSupport<T extends CloudProvider> extends AbstractProviderService<T> implements ContainerSupport {

    protected AbstractContainerSupport(T provider) {
        super(provider);
    }

    @Override
    public @Nonnull Iterable<Cluster> listClusters() throws CloudException, InternalException {
        throw new OperationNotSupportedException("Listing container clusters is not currently implemented for " + getProvider().getCloudName());
    }

    @Nonnull
    @Override
    public Iterable<Scheduler> listSchedulers() throws CloudException, InternalException {
        throw new OperationNotSupportedException("Listing container schedulers is not currently implemented for " + getProvider().getCloudName());
    }

    @Nonnull
    @Override
    public String createCluster(@Nonnull String clusterName) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Creating container clusters is not currently implemented for " + getProvider().getCloudName());
    }

    @Nullable
    @Override
    public Cluster getCluster(@Nonnull String providerClusterId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Retrieval of container clusters is not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public void removeCluster(@Nonnull String providerClusterId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Removing container clusters is not currently implemented for " + getProvider().getCloudName());
    }

    @Nonnull
    @Override
    public String createScheduler(@Nonnull SchedulerCreateOptions opts) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Creating container schedulers is not currently implemented for " + getProvider().getCloudName());
    }

    @Nullable
    @Override
    public Scheduler getScheduler(@Nonnull String providerSchedulerId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Retrieval of container schedulers is not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public void removeScheduler(@Nonnull String providerSchedulerId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Removing container schedulers is not currently implemented for " + getProvider().getCloudName());
    }
}
