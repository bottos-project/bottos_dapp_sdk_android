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

2、Gradle引入 [ ![Download](https://api.bintray.com/packages/xionglihui999/maven/bottos_dapp_sdk_android/images/download.svg?version=1.0.5) ](https://bintray.com/xionglihui999/maven/bottos_dapp_sdk_android/1.0.5/link)
```
allprojects {
    repositories {
        google()
        jcenter()
        maven {
            url "https://dl.bintray.com/xionglihui999/maven/"
        }
    }
}
```
```
dependencies {
    implementation 'com.bottos.botc.sdk:bottos_dapp_sdk_android:1.0.4'
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
| msg                      |String     | 响应描述                                 |
| result                   | jsonObject | 响应结果                                 |
| head_block_num           | long     | 块号                                     |
| head_block_hash          | String     | 前一块哈希值                             |
| head_block_time          | long     | 块生成时间                               |
| head_block_delegate      | String     | 块生产者                                 |
| cursor_label             | long     | 块标识                                   |
| last_consensus_block_num | long     | 不可逆块号                               |
| chain_id                 | String     | 链ID，同一链的所有节点的Chain_id必须相同 |
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
| password                      | String     | keyStore密码                          |
| privateKey                   | String | 私钥                                 |

返回数据格式如下：

```
{"account":"xionglihui5920","crypto":{"cipher":"aes-128-ctr","cipherparams":{"iv":"d7c990cde7be1bfa53499fd581aa10e4"},"ciphertext":"c5bda5a4a9bb515141a67f23739e431eb021efcb69da3c6369fc0b66675385e1","kdf":"scrypt","kdfparams":{"dklen":32,"n":1024,"p":1,"r":8,"salt":"f1a8ae6a44d7ebbec6466ae49ff1e945f554b95f1d45148ed70c9cecfb21d88b"},"mac":"2e295f1b0ab96a5929e5d706151dfa94945ad03dc52bcf8bdbd944dff158f2fc"},"id":"2dfd3ec2-41e5-45b1-9286-7bd69e5cd05f","version":"3"}
```

#### 3、根据Keystore与密码解出私钥
```
String privateKey=walletService.recoverKeystore(pwd,keystore);
```

#### 4、生成助记词
```
String mnemonicStr=walletService.createMnemonic();
```

#### 5、验证助记词是否正确(助记词错误会抛出异常)
```
walletService.validateMnemonic();
```
#### 6、根据助记词生成keystore
```
String keystore=walletService.hroughMnemonicsCreateKeystore( account,  mnemonicStr,  pwd) 
```

| 参数                     | 类型       | 说明                                     |
| ------------------------ | ---------- | ---------------------------------------- |
| accountName                  | String     | bottos账户     |
| mnemonicStr                      | String     | 助记词                          |
| password                   | String | keyStore密码                                 |


#### 7、查询交易状态(交易操作的最终结果都是根据此方法来查询,结果为执行失败或者交易已成功生效才是交易的最终状态，其余的状态时需要多次查询)

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
| errcode | String     | 错误码，0-相应成功，其他见错误码章节                         |
| msg     | String     | 响应描述                                                     |
| result  | Object | 响应结果                                                     |
| status  | String     | 查询交易状态结果，<br />”pending“：交易已提交，但未处理；<br />”packed“：交易已打包；<br />”not found“：交易执行失败；<br />”committed“：交易已成功生效 |

#### 8、交易操作-创建账号

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
| version      | String     | 链版本号                                   |
| cursor_num   | String     | 最新区块号，调用获取区块头获得             |
| cursor_label | String     | 最新区块标签，调用获取区块头获得           |
| lifetime     | String     | 交易过期时间，调用获取区块头，加一定的延时 |
| sender       | String     | 发送者                                     |
| contract     | String     | 合约名称                                   |
| method       | String     | 合约方法                                   |
| param        | Object | 业务参数                                   |
| sig_alg      | String     | 签名算法                                   |
| signature    | String     | 签名值                                     |

#### 9、交易操作-转账

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

#### 10、交易操作-质押

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

#### 11、交易操作-解质押
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

#### 12、交易操作-解质押之后提现
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

#### 13、交易操作-多签
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

#### 14、查询账户信息

```
walletService.newMsignAccount(AccountInfoRequest accountInfoRequest, RequestCallBackImp<AccountInfoResponse> requestCallBackImp)
```
AccountInfoRequest 参数说明

| 参数                     | 类型       | 说明                                     |
| ------------------------ | ---------- | ---------------------------------------- |
| account_name                  | String     | bottos账号     |

AccountInfoResponse参数说明

| 参数              | 类型       | 说明                                 |
| ----------------- | ---------- | ------------------------------------ |
|  |  |  |
| errcode           | String     | 错误码，0-相应成功，其他见错误码章节 |
| msg               | string     | 响应描述                             |
| result            | Object | 响应结果                             |
| account_name      | String     | 账号名称                             |
| pubkey            | String     | 公钥                                 |
| balance                 | String     | 账号可支配的BTO数量                   |
| staked_balance          | String     | 质押投票的BTO数量                     |
| staked_space_balance    | String     | 质押SPACE的BTO数量（交易需消耗SPACE） |
| staked_time_balance     | String     | 质押TIME的BTO数量（交易需消耗TIME） |
| unStaking_balance       | String     | 正解质押的BTO数量                     |
| unStaking_timestamp | long     | 解质押的时间（ Unix时间戳 ）         |
| authority | Object | 授权列表，若该账号非多签账号，不为空 |
| author_account | String | 授权账号 |
| weight | long | 权重 |
| resource | Object | 账号资源情况 |
| free_available_space   | String     | 免费额度内，可使用的SPACE数量         |
| free_used_space        | String     | 免费额度内，已使用的SPACE数量         |
| stake_available_space  | String     | 质押范围内，可使用的SPACE数量         |
| stake_used_space       | String     | 质押范围内，已使用的SPACE数量         |
| free_available_time    | String     | 免费额度内，可使用的TIME数量          |
| free_used_time         | String     | 免费额度内，已使用的TIME数量          |
| stake_available_time   | String     | 质押范围内，可使用的TIME数量          |
| stake_used_time        | String     | 质押范围内，已使用的TIME数量          |
| unClaimed_block_reward | String     | 出块奖励数                     |
| unClaimed_vote_reward | String     | 投票奖励数                |
| deploy_contract_list | String | 该账号部署的合约列表 |
| vote | Object | 投票信息 |
| delegate | String | 被投票生产者 |
| votes | String | 投票数量 |

备注：balance、staked_balance、staked_space_balance、staked_time_balance、unStaking_balance 三者之和为改账户总的Token值 。

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




