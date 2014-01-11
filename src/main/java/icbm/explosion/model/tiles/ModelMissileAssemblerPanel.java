// Date: 10/13/2013 11:18:56 PM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package icbm.explosion.model.tiles;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelMissileAssemblerPanel extends ModelBase
{
    // fields
    ModelRenderer base;
    ModelRenderer ridge;

    public ModelMissileAssemblerPanel()
    {
        textureWidth = 512;
        textureHeight = 512;

        base = new ModelRenderer(this, 0, 0);
        base.addBox(-24F, 0F, -8F, 48, 2, 16);
        base.setRotationPoint(0F, 22F, 0F);
        base.setTextureSize(64, 32);
        base.mirror = true;
        setRotation(base, 0F, 0F, 0F);
        ridge = new ModelRenderer(this, 0, 0);
        ridge.addBox(-22F, -2F, -5F, 44, 1, 10);
        ridge.setRotationPoint(0F, 23F, 0F);
        ridge.setTextureSize(64, 32);
        ridge.mirror = true;
        setRotation(ridge, 0F, 0F, 0F);
    }

    public void render(float f5)
    {
        base.render(f5);
        ridge.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

}
