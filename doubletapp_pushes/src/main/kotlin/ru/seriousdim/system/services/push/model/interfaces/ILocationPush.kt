package ru.seriousdim.system.services.push.model.interfaces

interface ILocationPush: IPush {
    var x: Float;
    var y: Float;
    var radius: Int;
}