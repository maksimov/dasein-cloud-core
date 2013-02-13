package org.dasein.cloud.compute;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.ProviderContext;
import org.dasein.cloud.Requirement;
import org.dasein.cloud.ResourceStatus;
import org.dasein.cloud.Tag;
import org.dasein.cloud.identity.ServiceAction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Basic non-functional functionality for any implementation of snapshot support in any cloud.
 * <p>Created by George Reese: 2/4/13 2:30 PM</p>
 * @author George Reese
 * @since 2013.04
 * @version 2013.04
 */
public abstract class AbstractSnapshotSupport implements SnapshotSupport {
    private CloudProvider provider;

    public AbstractSnapshotSupport(@Nonnull CloudProvider provider) {
        this.provider = provider;
    }

    @Override
    public void addSnapshotShare(@Nonnull String providerSnapshotId, @Nonnull String accountNumber) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Snapshot sharing is not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public void addPublicShare(@Nonnull String providerSnapshotId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Snapshot public sharing is not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public @Nullable String createSnapshot(@Nonnull SnapshotCreateOptions options) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Snapshot creation/copying is not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    @Deprecated
    public final @Nullable String create(@Nonnull String ofVolume, @Nonnull String description) throws InternalException, CloudException {
        return createSnapshot(SnapshotCreateOptions.getInstanceForCreate(ofVolume, description, description));
    }

    /**
     * @return the current authentication context for any calls through this support object
     * @throws CloudException no context was set
     */
    protected @Nonnull ProviderContext getContext() throws CloudException {
        ProviderContext ctx = getProvider().getContext();

        if( ctx == null ) {
            throw new CloudException("No context was specified for this request");
        }
        return ctx;
    }

    /**
     * @return the provider object associated with any calls through this support object
     */
    protected @Nonnull CloudProvider getProvider() {
        return provider;
    }

    @Override
    public @Nullable Snapshot getSnapshot(@Nonnull String snapshotId) throws InternalException, CloudException {
        for( Snapshot snapshot : listSnapshots() ) {
            if( snapshot.getProviderSnapshotId().equals(snapshotId) ) {
                return snapshot;
            }
        }
        return null;
    }

    @Override
    public @Nonnull Requirement identifyAttachmentRequirement() throws InternalException, CloudException {
        return Requirement.OPTIONAL;
    }

    @Override
    public boolean isPublic(@Nonnull String snapshotId) throws InternalException, CloudException {
        return false;
    }

    @Override
    public @Nonnull Iterable<String> listShares(@Nonnull String snapshotId) throws InternalException, CloudException {
        return Collections.emptyList();
    }

    @Override
    public @Nonnull Iterable<ResourceStatus> listSnapshotStatus() throws InternalException, CloudException {
        ArrayList<ResourceStatus> status = new ArrayList<ResourceStatus>();

        for( Snapshot snapshot : listSnapshots() ) {
            status.add(new ResourceStatus(snapshot.getProviderSnapshotId(), snapshot.getCurrentState()));
        }
        return status;
    }

    public @Nonnull Iterable<Snapshot> listSnapshots(SnapshotFilterOptions options) throws InternalException, CloudException {
        ArrayList<Snapshot> snapshots = new ArrayList<Snapshot>();

        for( Snapshot snapshot : listSnapshots() ) {
            if( options.matches(snapshot, getContext().getAccountNumber()) ) {
                snapshots.add(snapshot);
            }
        }
        return snapshots;
    }

    @Override
    public @Nonnull String[] mapServiceAction(@Nonnull ServiceAction action) {
        return new String[0];
    }

    @Override
    public void remove(@Nonnull String snapshotId) throws InternalException, CloudException {
        throw new OperationNotSupportedException("Snapshot removal is not currently supported for " + getProvider().getCloudName());
    }

    @Override
    public void removeAllSnapshotShares(@Nonnull String providerSnapshotId) throws CloudException, InternalException {
        // NO-OP
    }

    @Override
    public void removeSnapshotShare(@Nonnull String providerSnapshotId, @Nonnull String accountNumber) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Snapshot sharing is not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public void removePublicShare(@Nonnull String providerSnapshotId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Snapshot public sharing is not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public void removeTags(@Nonnull String snapshotId, @Nonnull Tag... tags) throws CloudException, InternalException {
        // NO-OP
    }

    @Override
    public void removeTags(@Nonnull String[] snapshotIds, @Nonnull Tag... tags) throws CloudException, InternalException {
        for( String id : snapshotIds ) {
            removeTags(id, tags);
        }
    }

    @Override
    @Deprecated
    public @Nonnull Iterable<Snapshot> searchSnapshots(@Nullable String ownerId, @Nullable String keyword) throws InternalException, CloudException {
        SnapshotFilterOptions options = SnapshotFilterOptions.getInstance();

        if( ownerId != null ) {
            options.withAccountNumber(ownerId);
        }
        if( keyword != null ) {
            options.matchingRegex(keyword);
        }
        return searchSnapshots(options);
    }

    @Override
    public @Nonnull Iterable<Snapshot> searchSnapshots(@Nonnull SnapshotFilterOptions options) throws InternalException, CloudException {
        if( !options.hasCriteria() ) {
            return listSnapshots();
        }
        ArrayList<Snapshot> snapshots = new ArrayList<Snapshot>();

        for( Snapshot snapshot : listSnapshots() ) {
            if( options.matches(snapshot, null) ) {
                snapshots.add(snapshot);
            }
        }
        return snapshots;
    }

    @Override
    @Deprecated
    public final void shareSnapshot(@Nonnull String snapshotId, @Nullable String withAccountId, boolean affirmative) throws InternalException, CloudException {
        if( affirmative ) {
            if( withAccountId != null ) {
                addSnapshotShare(snapshotId, withAccountId);
            }
            else {
                addPublicShare(snapshotId);
            }
        }
        else {
            if( withAccountId != null ) {
                removeSnapshotShare(snapshotId, withAccountId);
            }
            else {
                removePublicShare(snapshotId);
            }
        }
    }

    @Override
    @Deprecated
    public final @Nullable Snapshot snapshot(@Nonnull String volumeId, @Nonnull String name, @Nonnull String description, @Nullable Tag... tags) throws InternalException, CloudException {
        SnapshotCreateOptions options = SnapshotCreateOptions.getInstanceForCreate(volumeId, name, description);

        if( tags != null && tags.length > 0 ) {
            for( Tag t : tags ) {
                options.withMetaData(t.getKey(), t.getValue());
            }
        }
        String id = createSnapshot(options);

        if( id == null ) {
            return null;
        }
        return getSnapshot(id);
    }

    @Override
    public boolean supportsSnapshotCopying() throws CloudException, InternalException {
        return false;
    }

    @Override
    public boolean supportsSnapshotCreation() throws CloudException, InternalException {
        return false;
    }

    @Override
    public boolean supportsSnapshotSharing() throws InternalException, CloudException {
        return false;
    }

    @Override
    public boolean supportsSnapshotSharingWithPublic() throws InternalException, CloudException {
        return false;
    }

    @Override
    public @Nonnull String toString() {
        return (getProvider().getProviderName() + "/" + getProvider().getCloudName() + "/Compute/Snapshots");
    }

    @Override
    public void updateTags(@Nonnull String snapshotId, @Nonnull Tag... tags) throws CloudException, InternalException {
        // NO-OP
    }

    @Override
    public void updateTags(@Nonnull String[] snapshotIds, @Nonnull Tag... tags) throws CloudException, InternalException {
        for( String id : snapshotIds ) {
            updateTags(id, tags);
        }
    }
}
