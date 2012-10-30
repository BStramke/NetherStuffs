@echo off
mkdir reobf\minecraft\NetherStuffsBase
mkdir reobf\minecraft\NetherStuffsCoreBase
move reobf\minecraft\NetherStuffs reobf\minecraft\NetherStuffsBase
move reobf\minecraft\NetherStuffsCore reobf\minecraft\NetherStuffsCoreBase
copy NetherStuffs_Resources\mcmod.info reobf\minecraft\NetherStuffsBase\mcmod.info
copy NetherStuffs_Resources\textures\* reobf\minecraft\NetherStuffsBase
copy NetherStuffs_Resources\gui\* reobf\minecraft\NetherStuffsBase
mkdir reobf\minecraft\NetherStuffsCoreBase\META-INF
copy src\common\netherstuffs_at.cfg reobf\minecraft\NetherStuffsCoreBase\netherstuffs_at.cfg
copy src\common\NetherStuffsCore\META-INF\MANIFEST.MF reobf\minecraft\NetherStuffsCoreBase\META-INF\MANIFEST.MF
cd reobf\minecraft\NetherStuffsBase
"C:\Program Files\7-Zip\7z.exe" a -tzip mod_NetherStuffs.zip *
cd..
cd NetherStuffsCoreBase
"C:\Program Files\7-Zip\7z.exe" a -tzip mod_NetherStuffsCore.zip *
cd ..
move NetherStuffsBase\mod_NetherStuffs.zip mod_NetherStuffs.zip
move NetherStuffsCoreBase\mod_NetherStuffsCore.zip mod_NetherStuffsCore.jar
pause