package ru.seriousdim.system.services.push.model.interfaces

interface IExpiringPush: IPush {
    var expiryDate: Long;
}