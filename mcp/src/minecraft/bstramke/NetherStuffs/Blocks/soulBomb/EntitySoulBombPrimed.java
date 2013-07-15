package bstramke.NetherStuffs.Blocks.soulBomb;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntitySoulBombPrimed extends EntityTNTPrimed implements IEntityAdditionalSpawnData {

	public EntitySoulBombPrimed(World par1World) {
		super(par1World);
	}

	public EntitySoulBombPrimed(World par1World, double par2, double par4, double par6, EntityLivingBase par8EntityLiving) {
		super(par1World, par2, par4, par6, par8EntityLiving);
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.motionY -= 0.03999999910593033D;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.9800000190734863D;
		this.motionY *= 0.9800000190734863D;
		this.motionZ *= 0.9800000190734863D;

		if (this.onGround) {
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
			this.motionY *= -0.5D;
		}

		if (this.fuse-- <= 0) {
			this.setDead();

			if (!this.worldObj.isRemote) {
				this.explode();
			}
		} else {
			this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
		}
	}

	private void explode() {
		float var1 = 4.0F; // explosion size
		new SoulExplosion(this.worldObj, (Entity) this, this.posX, this.posY, this.posZ, var1);
	}
	
	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {
		data.writeInt(this.fuse);
	}

	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		this.fuse = data.readInt();
	}

}
