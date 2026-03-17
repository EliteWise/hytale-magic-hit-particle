package dev.hytalemodding.events;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector4d;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.EntityModule;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageEventSystem;
import com.hypixel.hytale.server.core.universe.world.ParticleUtil;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class PlayerDamageSystem extends DamageEventSystem {

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return Query.any();
    }

    @Override
    public void handle(int i, @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer, @NonNullDecl Damage damage) {
        //Player player = archetypeChunk.getComponent(i, Player.getComponentType());

        Ref<EntityStore> targetRef = archetypeChunk.getReferenceTo(i);

        TransformComponent transform = store.getComponent(targetRef, EntityModule.get().getTransformComponentType());
        if (transform == null) {
            System.out.println("TransformComponent null !");
            return;
        }

        Damage.Source source = damage.getSource();

        if (!(source instanceof Damage.EntitySource)) return;
        Damage.EntitySource entitySource = (Damage.EntitySource) source;
        Ref<EntityStore> attackerRef = entitySource.getRef();

        Player attacker = store.getComponent(attackerRef, Player.getComponentType());
        if (attacker == null) return;

        Vector4d hitLoc = damage.getIfPresentMetaObject(Damage.HIT_LOCATION);
        Vector3d hitPos;
        if (hitLoc != null) {
            hitPos = new Vector3d(hitLoc.x, hitLoc.y, hitLoc.z);
        } else {
            hitPos = transform.getPosition();
        }

        ParticleUtil.spawnParticleEffect("Magic_Hit", hitPos, store);
    }
}
