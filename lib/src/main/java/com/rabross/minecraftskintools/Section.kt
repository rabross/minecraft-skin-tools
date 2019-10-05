package com.rabross.minecraftskintools

/**
 * Represents an area or section of a complete Minecraft skin texture
 */
sealed class Section(val x: Int, val y: Int, val width: Int, val height: Int) {
    class Head : Section(8, 8, 8, 8)
    class Body : Section(20, 20, 8, 12)
    class ArmRight : Section(44, 20, 4, 12)
    class ArmLeft : Section(36, 52, 4, 12)
    class LegRight : Section(4, 20, 4, 12)
    class LegLeft : Section(20, 52, 4, 12)
    class HeadOverlay : Section(40, 8, 8, 8)
    class BodyOverlay : Section(20, 36, 8, 12)
    class ArmRightOverlay : Section(44, 36, 4, 12)
    class ArmLeftOverlay : Section(52, 52, 4, 12)
    class LegRightOverlay : Section(4, 36, 4, 12)
    class LegLeftOverlay : Section(4, 52, 4, 12)
}
