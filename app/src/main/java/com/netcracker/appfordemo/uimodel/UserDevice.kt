package com.netcracker.appfordemo.uimodel

import java.math.BigInteger

/**
 * Created by mozil on 21.01.2018.
 */
class UserDevice(val id: String?, val parentId: BigInteger?, val name: String?, val deviceType: ListType?, val macAddress: String?, val users: List<User>?, val groups: List<UserGroup>?,
                 val limit: String?, val consumed: String?)