package net.minecraft.server;

// CraftBukkit start
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.craftbukkit.inventory.CraftItemStack;

// CraftBukkit end

public class EntityCow extends EntityAnimal {

	public EntityCow(World world) {
		super(world);
		this.a(0.9F, 1.3F);
		getNavigation().a(true);
		goalSelector.a(0, new PathfinderGoalFloat(this));
		goalSelector.a(1, new PathfinderGoalPanic(this, 2.0D));
		goalSelector.a(2, new PathfinderGoalBreed(this, 1.0D));
		goalSelector.a(3, new PathfinderGoalTempt(this, 1.25D, Items.WHEAT, false));
		goalSelector.a(4, new PathfinderGoalFollowParent(this, 1.25D));
		goalSelector.a(5, new PathfinderGoalRandomStroll(this, 1.0D));
		goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
		goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
	}

	@Override
	public boolean bk() {
		return true;
	}

	@Override
	protected void aD() {
		super.aD();
		getAttributeInstance(GenericAttributes.maxHealth).setValue(10.0D);
		getAttributeInstance(GenericAttributes.d).setValue(0.20000000298023224D);
	}

	@Override
	protected String t() {
		return "mob.cow.say";
	}

	@Override
	protected String aT() {
		return "mob.cow.hurt";
	}

	@Override
	protected String aU() {
		return "mob.cow.hurt";
	}

	@Override
	protected void a(int i, int j, int k, Block block) {
		makeSound("mob.cow.step", 0.15F, 1.0F);
	}

	@Override
	protected float bf() {
		return 0.4F;
	}

	@Override
	protected Item getLoot() {
		return Items.LEATHER;
	}

	@Override
	protected void dropDeathLoot(boolean flag, int i) {
		int j = random.nextInt(3) + random.nextInt(1 + i);

		int k;

		for (k = 0; k < j; ++k) {
			this.a(Items.LEATHER, 1);
		}

		j = random.nextInt(3) + 1 + random.nextInt(1 + i);

		for (k = 0; k < j; ++k) {
			if (isBurning()) {
				this.a(Items.COOKED_BEEF, 1);
			} else {
				this.a(Items.RAW_BEEF, 1);
			}
		}
	}

	@Override
	public boolean a(EntityHuman entityhuman) {
		ItemStack itemstack = entityhuman.inventory.getItemInHand();

		if (itemstack != null && itemstack.getItem() == Items.BUCKET && !entityhuman.abilities.canInstantlyBuild) {
			// CraftBukkit start - Got milk?
			org.bukkit.Location loc = getBukkitEntity().getLocation();
			org.bukkit.event.player.PlayerBucketFillEvent event = CraftEventFactory.callPlayerBucketFillEvent(entityhuman, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), -1, itemstack, Items.MILK_BUCKET);

			if (event.isCancelled())
				return false;

			if (--itemstack.count <= 0) {
				entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, CraftItemStack.asNMSCopy(event.getItemStack()));
			} else if (!entityhuman.inventory.pickup(new ItemStack(Items.MILK_BUCKET))) {
				entityhuman.drop(CraftItemStack.asNMSCopy(event.getItemStack()), false);
			}
			// CraftBukkit end

			return true;
		}  // Alkazia - craft milk bottle
        else if (itemstack != null && itemstack.getItem() == Items.GLASS_BOTTLE && !entityhuman.abilities.canInstantlyBuild) {
            // CraftBukkit start - Got milk?
            org.bukkit.Location loc = this.getBukkitEntity().getLocation();
            org.bukkit.event.player.PlayerBucketFillEvent event = CraftEventFactory.callPlayerBucketFillEvent(entityhuman, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), -1, itemstack, Items.potionMilk);

            if (event.isCancelled()) {
                return false;
            }

            if (--itemstack.count <= 0) {
                entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, CraftItemStack.asNMSCopy(event.getItemStack()));
            } else if (!entityhuman.inventory.pickup(new ItemStack(Items.potionMilk))) {
                entityhuman.drop(CraftItemStack.asNMSCopy(event.getItemStack()), false);
            }
            // CraftBukkit end

            return true;
        }else
			return super.a(entityhuman);
	}

	public EntityCow b(EntityAgeable entityageable) {
		return new EntityCow(world);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable) {
		return this.b(entityageable);
	}
}