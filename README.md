# Bottos Android SDK
## 概述
本文档详细说明 Bottos Android SDK常用接口文档, 使开发者更方便地操作和查询Bottos区块链。
## 名称解释
区块服务：提供查询区块信息接口

钱包服务：提供对账户类操作接口

abi文件参数:  bottos业务结构数据
## 集成Sdk
1、下载bottos android sdk
在Android Studio的项目工程导入bottos_dapp_sdk_android即可使用

2、Gradle引入 [ ![Download](https://api.bintray.com/packages/xionglihui99/maven/bottos_dapp_sdk_android/images/download.svg?version=1.0.1) ](https://bintray.com/xionglihui99/maven/bottos_dapp_sdk_android/1.0.1/link)  
```
allprojects {
    repositories {
        google()
        jcenter()
        maven {
            url "https://dl.bintray.com/xionglihui99/maven/"
        }
    }
}
```
```
dependencies {
    implementation 'com.bottos.botc.sdk:bottos_dapp_sdk_android:1.0.1'
}
```
## 使用方法
### 生成SDK实例
调用BottosSdkManger的接口getInstance来实现，具体调用如下:
```
Configure.init("http://servicenode1.chainbottos.com:8689/");
BottosSdkManger bottosSdkManger= BottosSdkManger.getInstance();
```
### 创建区块服务

获取区块信息
```
 BlockChainService  blockChainService=BottosSdkManger.getInstance().getBlickChainService();
 blockChainService.getBlockHeight(new RequestCallBackImp<BlockHeight>() {
            @Override
            public void onNext(BlockHeight blockHeight) {
            }
        });
```
BlockHeight响应字段

| 参数                     | 类型       | 说明                                     |
| ------------------------ | ---------- | ---------------------------------------- |
| errcode                  | int     | 错误码，0-相应成功，其他见错误码章节     |
| msg                      | string     | 响应描述                                 |
| result                   | jsonObject | 响应结果                                 |
| head_block_num           | long     | 块号                                     |
| head_block_hash          | string     | 前一块哈希值                             |
| head_block_time          | long     | 块生成时间                               |
| head_block_delegate      | string     | 块生产者                                 |
| cursor_label             | long     | 块标识                                   |
| last_consensus_block_num | long     | 不可逆块号                               |
| chain_id                 | string     | 链ID，同一链的所有节点的Chain_id必须相同 |
| head_block_version       | long     | 链版本号                           |


### 创建钱包服务

```
 WalletService  walletService=BottosSdkManger.getInstance().getWalletService();
```
#### 1、生成公私钥地址
```
String keys=walletService.createKeys();
```
keys为包含公私钥的json数据格式

| 参数                     | 类型       | 说明                                     |
| ------------------------ | ---------- | ---------------------------------------- |
| publicKey                | String     | 公钥    |
| privateKey               | String     | 私钥                       |

#### 2、创建bottos Keystore
```
String keystore=walletService.createKeystore(accountName,password,privateKey);
```
传参说明

| 参数                     | 类型       | 说明                                     |
| ------------------------ | ---------- | ---------------------------------------- |
| accountName                  | String     | bottos账户     |
| password                      | string     | keyStore密码                          |
| privateKey                   | String | 私钥                                 |

返回数据格式如下：

```
{"account":"xionglihui5920","crypto":{"cipher":"aes-128-ctr","cipherparams":{"iv":"d7c990cde7be1bfa53499fd581aa10e4"},"ciphertext":"c5bda5a4a9bb515141a67f23739e431eb021efcb69da3c6369fc0b66675385e1","kdf":"scrypt","kdfparams":{"dklen":32,"n":1024,"p":1,"r":8,"salt":"f1a8ae6a44d7ebbec6466ae49ff1e945f554b95f1d45148ed70c9cecfb21d88b"},"mac":"2e295f1b0ab96a5929e5d706151dfa94945ad03dc52bcf8bdbd944dff158f2fc"},"id":"2dfd3ec2-41e5-45b1-9286-7bd69e5cd05f","version":"3"}
```

#### 3、根据Keystore与密码解出私钥
```
String privateKey=walletService.recoverKeystore(pwd,keystore);
```
#### 4、查询交易状态(交易操作的最终结果都是根据此方法来查询)

```
walletService.getTransactionStatus(TransactionStatusRequest transactionStatusRequest,
RequestCallBackImp<CommonResponse<TransactionStatusResponse>> requestCallBackImp);
```
TransactionStatusRequest参数说明

| 参数                     | 类型       | 说明                                     |
| ------------------------ | ---------- | ---------------------------------------- |
| trx_hash                  | String     | 操作交易返回的hax     |

CommonResponse << TransactionStatusResponse>>响应参数说明


| 参数    | 类型       | 说明                                                         |
| ------- | ---------- | ------------------------------------------------------------ |
| errcode | uint32     | 错误码，0-相应成功，其他见错误码章节                         |
| msg     | string     | 响应描述                                                     |
| result  | jsonObject | 响应结果                                                     |
| status  | string     | 查询交易状态结果，<br />”pending“：交易已提交，但未处理；<br />”packed“：交易已打包；<br />”not found“：交易执行失败；<br />”committed“：交易已成功生效 |

#### 5、交易操作-创建账号

```
walletService.createAccount(CreateAccountParamsRequest createAccountParamsRequest, String send, String privateKey,
RequestCallBackImp<SendTransactionResponse> requestCallBackImp);
```
send表示发送人、引荐人
privateKey表示私钥
CreateAccountParamsRequest 参数说明

| 参数                     | 类型       | 说明                                     |
| ------------------------ | ---------- | ---------------------------------------- |
| name                  | String     | 需要创建的账号     |
| pubkey                  | String     | 公钥     |

交易操作返回参数都是同一个对象 SendTransactionResponse字段说明

| 参数                     | 类型       | 说明                                     |
| ------------------------ | ---------- | ---------------------------------------- |
| trx_hash                  | String     | 交易哈希值     |
| tradeInfo                  | TradeInfo     | 交易信息     |

TradeInfo字段说明

| 参数                     | 类型       | 说明                                     |
| ------------------------ | ---------- | ---------------------------------------- |
| version      | uint32     | 链版本号                                   |
| cursor_num   | uint64     | 最新区块号，调用获取区块头获得             |
| cursor_label | uint32     | 最新区块标签，调用获取区块头获得           |
| lifetime     | uint64     | 交易过期时间，调用获取区块头，加一定的延时 |
| sender       | string     | 发送者                                     |
| contract     | string     | 合约名称                                   |
| method       | string     | 合约方法                                   |
| param        | jsonObject | 业务参数                                   |
| sig_alg      | uint32     | 签名算法                                   |
| signature    | string     | 签名值                                     |

#### 6、交易操作-转账

```
walletService.transfer(TransferParamsRequest transferParamsRequest, String send, String privateKey,
RequestCallBackImp<SendTransactionResponse> requestCallBackImp)
```
send表示发送人
privateKey表示私钥

TransferParamsRequest 参数说明

| 参数                     | 类型       | 说明                                     |
| ------------------------ | ---------- | ---------------------------------------- |
| from                  | String     | 转出的账户     |
| to                  | String     | 转到的账户     |
| value                  | String     | 转账金额     |
| memo                  | String     | 备注     |

响应参数同创建账户

#### 7、交易操作-质押

```
walletService.stake(StakeParamsRequest stakeParamsRequest, String send, String privateKey,
RequestCallBackImp<SendTransactionResponse> requestCallBackImp)
```
send表示发送人
privateKey表示私钥

StakeParamsRequest 参数说明

| 参数                     | 类型       | 说明                                     |
| ------------------------ | ---------- | ---------------------------------------- |
| amount                  | String     | 质押金额     |
| target                  | String     | 仅在质押操作时使用：时间/空间 传入time/space字符串  time:质押时间资源 space:质押空间资源     |

target常量获取
```
Constants.SPACE
Constants.TIME
```
响应参数同创建账户

#### 8、交易操作-解质押
```
walletService.unStake(UnStakeParamsRequest unStakeParamsRequest, String send, String privateKey,
RequestCallBackImp<SendTransactionResponse> requestCallBackImp) 
```
send表示发送人
privateKey表示私钥

UnStakeParamsRequest 参数说明

| 参数                     | 类型       | 说明                                     |
| ------------------------ | ---------- | ---------------------------------------- |
| amount                  | String     | 质押金额     |
| source                  | String     | 仅在质押操作时使用：时间/空间 传入time/space字符串  time:质押时间资源 space:质押空间资源     |

target常量获取
```
Constants.SPACE
Constants.TIME
```
响应参数同创建账户

#### 9、交易操作-解质押之后提现
```
walletService.claim(ClaimParamsRequest claimParamsRequest, String send, String privateKey,
RequestCallBackImp<SendTransactionResponse> requestCallBackImp)
```
send表示发送人
privateKey表示私钥

ClaimParamsRequest 参数说明

| 参数                     | 类型       | 说明                                     |
| ------------------------ | ---------- | ---------------------------------------- |
| amount                  | String     | 提现金额     |

#### 10、交易操作-多签
```
walletService.newMsignAccount(NewMsignAccountRequest newMsignAccountRequests, String send, String privateKey,
RequestCallBackImp<SendTransactionResponse> requestCallBackImp)
```
send表示发送人
privateKey表示私钥

NewMsignAccountRequest 参数说明

| 参数                     | 类型       | 说明                                     |
| ------------------------ | ---------- | ---------------------------------------- |
| account                  | String     | 多签账户     |
| threshold                  | String     | 权重限制条件     |
| authority                  | List<< MsignAuthorInfo >>     | 提现金额     |

MsignAuthorInfo 参数说明

| 参数                     | 类型       | 说明                                     |
| ------------------------ | ---------- | ---------------------------------------- |
| author_account                  | String     | 能够操作多签的帐号     |
| weight                  | String     | 权重     |

### abi文件数据序列化与签名说明
下面是abi转账的参数来说明
```
	"name": "transfer",
		"base": "",
		"fields": {
			"from": "string",
			"to": "string",
			"value": "uint256",
			"memo": "string"
		}
```
#### 数据序列化
序列化的对象为fields字段里的业务字段,abi的业务参数都已固定和有序的数据格式来进行序列化，对字段进行通过调用SDK里面的MsgPack来对每个字段进行序列化进行有序组装

| 参数                     | 类型       | 调用方法                                     |返回值|
| ------------------------ | ---------- | ---------------------------------------- |-------|
|arsize(参数个数）                  | int     | MsgPack.packArraySize（4）    |long[]|
| from                  | String     | MsgPack.packStr16（String from）     |long[]|
| to                  | String     | MsgPack.packStr16(String to)     |long[]|
| value                  | uint256     | MsgPack.packUint256（String value）     |long[]|
| memo                  | String     | MsgPack.packStr16(String memo)     |long[]|
最终序列化的结果:

```
  long[] arsize = MsgPack.packArraySize(4);
  long[] from1 = MsgPack.packStr16("from");
  long[] to1 = MsgPack.packStr16("to");
  long [] value1= MsgPack.packUint256("value");;
  long []memo1=MsgPack.packStr16("meomo"));
  long [] param= ArraysUtils.arrayCopylong(arsize, from1, to1,value1,memo1);//序列化最终结果
```
#### 对序列化数据加签
通过区块服务获取BlockHeight信息，然后创建RequestDataSign对象进行赋值，调用SignaturedFetchParamUtils.getSignaturedFetchParam对序列化数据进行加签
```
 RequestDataSign requestDataSign = new RequestDataSign();
 requestDataSign.setVersion(blockHeight.getHead_block_version());
 requestDataSign.setCursor_num(blockHeight.getHead_block_num());
 requestDataSign.setCursor_label(blockHeight.getCursor_label());
 requestDataSign.setLifetime(blockHeight.getHead_block_time() + 100);
 requestDataSign.setSender(send);
 requestDataSign.setMethod(method);
 requestDataSign.setContract(Constants.SEND_TRANSACTION_CONTRACT);
 requestDataSign.setSig_alg(1);
 requestDataSign.setParam(CryptTool.getHex16(params));
 requestDataSign.setSignature(SignaturedFetchParamUtils.getSignaturedFetchParam(requestDataSign,params,
 privateKey, blockHeight.getChain_id()));//最终加签数据结果
```




