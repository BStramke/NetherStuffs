@echo off
cd mcp
mkdir reobf\minecraft\bstramke\NetherStuffsBase
mkdir reobf\minecraft\bstramke\NetherStuffsBase\bstramke
mkdir reobf\minecraft\bstramke\NetherStuffsBase\buildcraft
mkdir reobf\minecraft\bstramke\NetherStuffsBase\buildcraft\api
mkdir reobf\minecraft\bstramke\NetherStuffsBase\buildcraft\api\inventory
mkdir reobf\minecraft\bstramke\NetherStuffsCoreBase
mkdir reobf\minecraft\bstramke\NetherStuffsCoreBase\bstramke
move reobf\minecraft\bstramke\NetherStuffs reobf\minecraft\bstramke\NetherStuffsBase\bstramke
move reobf\minecraft\bstramke\NetherStuffsNEIPlugin reobf\minecraft\bstramke\NetherStuffsBase\bstramke
move reobf\minecraft\bstramke\NetherStuffsCore reobf\minecraft\bstramke\NetherStuffsCoreBase\bstramke

mkdir reobf\minecraft\bstramke\NetherStuffsBase\bstramke\NetherStuffs\resources
mkdir reobf\minecraft\bstramke\NetherStuffsBase\bstramke\NetherStuffs\resources\textures
mkdir reobf\minecraft\bstramke\NetherStuffsBase\bstramke\NetherStuffs\resources\textures\gui

xcopy reobf\minecraft\forestry reobf\minecraft\bstramke\NetherStuffsBase\forestry\ /S
copy reobf\minecraft\buildcraft\api\inventory\* reobf\minecraft\bstramke\NetherStuffsBase\buildcraft\api\inventory
copy src\minecraft\bstramke\NetherStuffs\mcmod.info reobf\minecraft\bstramke\NetherStuffsBase\mcmod.info
copy src\minecraft\bstramke\NetherStuffs\resources\* reobf\minecraft\bstramke\NetherStuffsBase\bstramke\NetherStuffs\resources
copy src\minecraft\bstramke\NetherStuffs\resources\textures\* reobf\minecraft\bstramke\NetherStuffsBase\bstramke\NetherStuffs\resources\textures
copy src\minecraft\bstramke\NetherStuffs\resources\textures\gui\* reobf\minecraft\bstramke\NetherStuffsBase\bstramke\NetherStuffs\resources\textures\gui
mkdir reobf\minecraft\bstramke\NetherStuffsCoreBase\META-INF
copy src\minecraft\bstramke\netherstuffs_at.cfg reobf\minecraft\bstramke\NetherStuffsCoreBase\netherstuffs_at.cfg
copy src\minecraft\bstramke\NetherStuffsCore\META-INF\MANIFEST.MF reobf\minecraft\bstramke\NetherStuffsCoreBase\META-INF\MANIFEST.MF
rem copy Custom Class Files
rem BlockBreakable
copy reobf\minecraft\akm.class reobf\minecraft\bstramke\NetherStuffsCoreBase\akm.class 
rem BlockPane
copy reobf\minecraft\amp.class reobf\minecraft\bstramke\NetherStuffsCoreBase\amp.class 
rem Chunk
copy reobf\minecraft\zz.class reobf\minecraft\bstramke\NetherStuffsCoreBase\zz.class 
cd reobf\minecraft\bstramke\NetherStuffsBase
"C:\Program Files\7-Zip\7z.exe" a -tzip mod_NetherStuffs.zip *
cd..
cd NetherStuffsCoreBase
"C:\Program Files\7-Zip\7z.exe" a -tzip mod_NetherStuffsCore.zip *
cd ..
move NetherStuffsBase\mod_NetherStuffs.zip mod_NetherStuffs.zip
move NetherStuffsCoreBase\mod_NetherStuffsCore.zip mod_NetherStuffsCore.jar
cd D:\GitHub\NetherStuffs
copy mcp\reobf\minecraft\bstramke\mod_NetherStuffs.zip mod_NetherStuffs.zip
copy mcp\reobf\minecraft\bstramke\mod_NetherStuffsCore.jar mod_NetherStuffsCore.jar
pause