/*
 * *****************************************************************************************************************************
 * Copyright 2019-2020 NXP.
 * NXP Confidential. This software is owned or controlled by NXP and may only be used strictly in accordance with the applicable license terms.
 * By expressly accepting such terms or by downloading, installing, activating and/or otherwise using the software, you are agreeing that you have read, and that you agree to comply with and are bound by, such license terms.
 * If you do not agree to be bound by the applicable license terms, then you may not retain, install, activate or otherwise use the software.
 * ********************************************************************************************************************************
 *
 */

package im.sma.taplinxtest;

/**
 * Keys used by the Sample Application are declared here.
 * Created by nxp69547 on 8/3/2016.
 */
public final class SampleAppKeys {

    /**
     * Default key with Value FF.
     */
    public static final byte[] KEY_DEFAULT_FF = {(byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
    /**
     * 16 bytes AES128 Key.
     */
    public static final byte[] KEY_AES128 = {(byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
    /**
     * 16 bytes AES128 Key.
     */
    public static final byte[] KEY_AES128_ZEROS = {(byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
    /**
     * 24 bytes 2KTDES Key.
     */
    public static final byte[] KEY_2KTDES = {(byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
    /**
     * 16 bytes 2KTDES_ULC Key.
     */
    public static final byte[] KEY_2KTDES_ULC = {(byte) 0x49, (byte) 0x45,
            (byte) 0x4D, (byte) 0x4B, (byte) 0x41, (byte) 0x45, (byte) 0x52,
            (byte) 0x42, (byte) 0x21, (byte) 0x4E, (byte) 0x41, (byte) 0x43,
            (byte) 0x55, (byte) 0x4F, (byte) 0x59, (byte) 0x46};

    /**
     *
     * Private constructor restricts Implementation.
     */
    /**/
//            8C-B2-95-7F-EB-56-0A-D7-26-F0-5E-E6-FC-AF-CC-D4
    public static final byte[] DEFAULT_KEY = {
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00};

    public static final byte[] KEY_3DES_ULC1 = {
            (byte) 0x8C, (byte) 0xB2, (byte) 0x95, (byte) 0x7F, (byte) 0xEB,
            (byte) 0x56, (byte) 0x0A, (byte) 0xD7, (byte) 0x26, (byte) 0xF0,
            (byte) 0x5E, (byte) 0xE6, (byte) 0xFC, (byte) 0xAF, (byte) 0xCC,
            (byte) 0xD4};
    /**/
    private SampleAppKeys() {

    }

    /**
     * Only these types of Keys can be stored by the Helper class.
     */
    public enum EnumKeyType {
        EnumAESKey,
        EnumDESKey,
        EnumMifareKey
    }
}
