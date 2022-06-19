package knz.paint.model.effects.specific.hsba.colorblindness;

public class TritanopiaEffect extends AbstractColorBlindnessEffect {

    public TritanopiaEffect() {
        super("Tritanopia",
            new int[] {
            0x04, 0x04, 0x04, 0x03, 0x03, 0x03, 0x01, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xFE, 0xFE, 0xFE, 0xFE, 0xFE, 0xFE,
            0xFE, 0xFE, 0xFD, 0xFD, 0xFD, 0xFD, 0xFD, 0xFC, 0xFC, 0xFC, 0xFC, 0xFC, 0xFB, 0xFB, 0xFB, 0xFB, 0xFB, 0xFA, 0xFA, 0xFA,
            0xFA, 0xFA, 0xF9, 0xF9, 0xF8, 0xF8, 0xF8, 0xF7, 0xF7, 0xF6, 0xF6, 0xF5, 0xF4, 0xF3, 0xF2, 0xF1, 0xEF, 0xEF, 0xED, 0xEB,
            0xEB, 0xE4, 0xE3, 0xDC, 0xD4, 0xCD, 0xC3, 0xBA, 0xB3, 0xAA, 0xA3, 0x9C, 0x99, 0x96, 0x94, 0x92, 0x90, 0x8F, 0x8E, 0x8D,
            0x8C, 0x8C, 0x8B, 0x8A, 0x8A, 0x89, 0x89, 0x88, 0x88, 0x88, 0x87, 0x87, 0x87, 0x87, 0x87, 0x86, 0x86, 0x86, 0x86, 0x86,
            0x86, 0x86, 0x86, 0x85, 0x85, 0x85, 0x85, 0x85, 0x85, 0x85, 0x85, 0x85, 0x85, 0x85, 0x85, 0x85, 0x85, 0x85, 0x85, 0x85,
            0x85, 0x85, 0x85, 0x85, 0x85, 0x85, 0x85, 0x85, 0x85, 0x84, 0x84, 0x84, 0x84, 0x84, 0x84, 0x84, 0x85, 0x85, 0x85, 0x85,
            0x85, 0x85, 0x85, 0x85, 0x85, 0x85, 0x85, 0x84, 0x84, 0x84, 0x84, 0x85, 0x85, 0x85, 0x85, 0x85, 0x85, 0x84, 0x84, 0x84,
            0x85, 0x85, 0x85, 0x85, 0x84, 0x84, 0x84, 0x84, 0x85, 0x85, 0x84, 0x84, 0x84, 0x84, 0x85, 0x85, 0x84, 0x84, 0x84, 0x85,
            0x85, 0x84, 0x84, 0x83, 0x83, 0x82, 0x82, 0x82, 0x82, 0x82, 0x82, 0x82, 0x82, 0x82, 0x82, 0x82, 0x82, 0x82, 0x82, 0x82,
            0x82, 0x82, 0x82, 0x82, 0x82, 0x82, 0x82, 0x82, 0x82, 0x81, 0x81, 0x82, 0x81, 0x81, 0x81, 0x82, 0x82, 0x81, 0x81, 0x81,
            0x81, 0x81, 0x81, 0x81, 0x81, 0x81, 0x81, 0x81, 0x81, 0x81, 0x81, 0x81, 0x81, 0x81, 0x81, 0x81, 0x81, 0x81, 0x81, 0x80,
            0x80, 0x80, 0x81, 0x81, 0x81, 0x81, 0x81, 0x81, 0x81, 0x81, 0x81, 0x81, 0x81, 0x81, 0x81, 0x81, 0x81, 0x81, 0x81, 0x82,
            0x82, 0x81, 0x82, 0x82, 0x83, 0x84, 0x86, 0x87, 0x89, 0x8B, 0x90, 0x97, 0xAA, 0xC4, 0xE2, 0xEC, 0xF1, 0xF3, 0xF5, 0xF7,
            0xF8, 0xF8, 0xF9, 0xF9, 0xFA, 0xFA, 0xFA, 0xFB, 0xFB, 0xFB, 0xFB, 0xFB, 0xFC, 0xFC, 0xFC, 0xFC, 0xFC, 0xFC, 0xFC, 0xFC,
            0xFC, 0xFD, 0xFD, 0xFD, 0xFD, 0xFD, 0xFD, 0xFD, 0xFD, 0xFD, 0xFD, 0xFD, 0xFD, 0xFD, 0xFD, 0xFE, 0xFE, 0xFE, 0xFE, 0xFE,
            0xFE, 0xFE, 0xFE, 0xFE, 0xFE, 0xFE, 0xFE, 0x00, 0x00, 0xFE, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x01, 0x01, 0x01, 0x01, 0x01, 0x02, 0x03, 0x03, 0x03, 0x03, 0x03, 0x03, 0x03, 0x04, 0x04, 0x04, 0x04, 0x04 },
            new int[] {
            0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xF0, 0xE9, 0xE3, 0xDE, 0xD8, 0xD3, 0xCF, 0xCA, 0xC6, 0xC2, 0xBE, 0xBA, 0xB6, 0xB2,
            0xAE, 0xAA, 0xA5, 0xA2, 0x9E, 0x9A, 0x95, 0x92, 0x8E, 0x8A, 0x85, 0x81, 0x7E, 0x7A, 0x75, 0x71, 0x6D, 0x6A, 0x65, 0x61,
            0x5D, 0x59, 0x55, 0x51, 0x4D, 0x49, 0x44, 0x41, 0x3D, 0x39, 0x34, 0x30, 0x2C, 0x29, 0x24, 0x20, 0x1C, 0x18, 0x13, 0x0F,
            0x0B, 0x0D, 0x0E, 0x10, 0x11, 0x12, 0x12, 0x12, 0x12, 0x12, 0x15, 0x1A, 0x1D, 0x21, 0x24, 0x28, 0x2C, 0x2F, 0x32, 0x36,
            0x3A, 0x3D, 0x40, 0x44, 0x47, 0x4A, 0x4E, 0x51, 0x54, 0x57, 0x5A, 0x5E, 0x61, 0x63, 0x66, 0x69, 0x6C, 0x6E, 0x71, 0x74,
            0x76, 0x78, 0x7A, 0x7D, 0x7F, 0x80, 0x82, 0x84, 0x85, 0x87, 0x88, 0x89, 0x8A, 0x8B, 0x8C, 0x8D, 0x8D, 0x8D, 0x8E, 0x8E,
            0x8E, 0x8E, 0x8E, 0x8E, 0x8E, 0x8E, 0x8D, 0x8D, 0x8D, 0x8D, 0x8D, 0x8D, 0x8C, 0x8C, 0x8C, 0x8C, 0x8B, 0x8B, 0x8A, 0x8A,
            0x8A, 0x89, 0x89, 0x88, 0x88, 0x87, 0x86, 0x86, 0x85, 0x85, 0x84, 0x83, 0x82, 0x82, 0x81, 0x80, 0x7F, 0x7E, 0x7D, 0x7C,
            0x7B, 0x7A, 0x79, 0x78, 0x77, 0x76, 0x75, 0x74, 0x73, 0x72, 0x70, 0x6F, 0x6E, 0x6D, 0x6B, 0x6A, 0x69, 0x67, 0x66, 0x64,
            0x63, 0x73, 0x86, 0xA3, 0xC6, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
            0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
            0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
            0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
            0xFF, 0xFF, 0xFF, 0xFF, 0xB7, 0x98, 0x79, 0x66, 0x53, 0x42, 0x2F, 0x21, 0x13, 0x13, 0x1B, 0x27, 0x2D, 0x36, 0x3F, 0x46,
            0x4C, 0x52, 0x59, 0x5E, 0x62, 0x67, 0x6C, 0x6F, 0x73, 0x76, 0x7A, 0x7D, 0x80, 0x83, 0x86, 0x88, 0x8B, 0x8D, 0x90, 0x92,
            0x94, 0x95, 0x97, 0x9A, 0x9B, 0x9E, 0xA0, 0xA2, 0xA3, 0xA6, 0xA7, 0xA9, 0xAB, 0xAD, 0xAF, 0xB1, 0xB2, 0xB4, 0xB5, 0xB8,
            0xBA, 0xBB, 0xBD, 0xBF, 0xC0, 0xC2, 0xC4, 0xC6, 0xC8, 0xCA, 0xCB, 0xCD, 0xCF, 0xD1, 0xD3, 0xD6, 0xD8, 0xDA, 0xDC, 0xDE,
            0xE0, 0xE2, 0xE5, 0xE8, 0xEA, 0xED, 0xF0, 0xF6, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF },
            new int[] {
            0xFD, 0xFD, 0xFD, 0xFE, 0xFE, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
            0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
            0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
            0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
            0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
            0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
            0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
            0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
            0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
            0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFD, 0xF9, 0xF4, 0xF0, 0xED, 0xE9, 0xE4, 0xE0, 0xDC, 0xD8, 0xD3, 0xD0, 0xCC, 0xC8, 0xC3,
            0xC0, 0xBC, 0xB8, 0xB4, 0xB0, 0xAC, 0xA9, 0xA4, 0xA1, 0x9D, 0x9A, 0x96, 0x92, 0x8F, 0x8C, 0x88, 0x85, 0x82, 0x7F, 0x7B,
            0x78, 0x76, 0x73, 0x70, 0x6E, 0x6B, 0x69, 0x67, 0x65, 0x63, 0x61, 0x60, 0x5E, 0x5D, 0x5C, 0x5B, 0x5B, 0x5A, 0x5A, 0x59,
            0x59, 0x59, 0x5A, 0x5A, 0x5A, 0x5A, 0x5A, 0x5B, 0x5B, 0x5C, 0x5C, 0x5D, 0x5D, 0x5E, 0x5F, 0x60, 0x61, 0x62, 0x63, 0x65,
            0x66, 0x67, 0x69, 0x6B, 0x6B, 0x6B, 0x6B, 0x6B, 0x6B, 0x6B, 0x6B, 0x6B, 0x6B, 0x6B, 0x6F, 0x75, 0x7A, 0x7F, 0x85, 0x8A,
            0x8F, 0x94, 0x9A, 0x9F, 0xA3, 0xA8, 0xAE, 0xB2, 0xB7, 0xBB, 0xC1, 0xC5, 0xCA, 0xCE, 0xD4, 0xD8, 0xDD, 0xE1, 0xE6, 0xEB,
            0xEF, 0xF0, 0xF0, 0xF1, 0xF1, 0xF2, 0xF2, 0xF3, 0xF3, 0xF4, 0xF4, 0xF5, 0xF5, 0xF6, 0xF6, 0xF7, 0xF7, 0xF7, 0xF8, 0xF8,
            0xF8, 0xF9, 0xF9, 0xF9, 0xFA, 0xFA, 0xFA, 0xFB, 0xFB, 0xFB, 0xFB, 0xFC, 0xFC, 0xFC, 0xFC, 0xFD, 0xFD, 0xFD, 0xFD, 0xFD,
            0xFE, 0xFE, 0xFE, 0xFE, 0xFE, 0xFE, 0xFE, 0xFE, 0xFE, 0xFE, 0xFE, 0xFE, 0xFE, 0xFD, 0xFD, 0xFD, 0xFD, 0xFD, 0xFD, 0xFD });
    }

}