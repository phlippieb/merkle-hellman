ant clean -q
ant jar -Dconfig=GenerateKeys -q
cp dist/COS720A3.jar ./generateKeys.jar 
ant jar -Dconfig=Encrypt -q
cp dist/COS720A3.jar ./encrypt.jar 
ant jar -Dconfig=Decrypt -q
cp dist/COS720A3.jar ./decrypt.jar 

