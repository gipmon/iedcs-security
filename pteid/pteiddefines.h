#ifndef __PTEIDDEFINES_H__
#define __PTEIDDEFINES_H__

/* Fields length */
#define PTEID_DELIVERY_ENTITY_LEN               40
#define PTEID_COUNTRY_LEN                       80
#define PTEID_DOCUMENT_TYPE_LEN                 34
#define PTEID_CARDNUMBER_LEN                    28
#define PTEID_CARDNUMBER_PAN_LEN                32
#define PTEID_CARDVERSION_LEN                   16
#define PTEID_DATE_LEN                          20
#define PTEID_LOCALE_LEN                        60
#define PTEID_NAME_LEN                          120
#define PTEID_SEX_LEN                           2
#define PTEID_NATIONALITY_LEN                   6
#define PTEID_HEIGHT_LEN                        8
#define PTEID_NUMBI_LEN                         18
#define PTEID_NUMNIF_LEN                        18
#define PTEID_NUMSS_LEN                         22
#define PTEID_NUMSNS_LEN                        18
#define PTEID_INDICATIONEV_LEN                  120
#define PTEID_MRZ_LEN							30

#define PTEID_MAX_DELIVERY_ENTITY_LEN               PTEID_DELIVERY_ENTITY_LEN+2
#define PTEID_MAX_COUNTRY_LEN                       PTEID_COUNTRY_LEN+2
#define PTEID_MAX_DOCUMENT_TYPE_LEN                 PTEID_DOCUMENT_TYPE_LEN+2
#define PTEID_MAX_CARDNUMBER_LEN                    PTEID_CARDNUMBER_LEN+2
#define PTEID_MAX_CARDNUMBER_PAN_LEN                PTEID_CARDNUMBER_PAN_LEN+2
#define PTEID_MAX_CARDVERSION_LEN                   PTEID_CARDVERSION_LEN+2
#define PTEID_MAX_DATE_LEN                          PTEID_DATE_LEN+2
#define PTEID_MAX_LOCALE_LEN                        PTEID_LOCALE_LEN+2
#define PTEID_MAX_NAME_LEN                          PTEID_NAME_LEN+2
#define PTEID_MAX_SEX_LEN                           PTEID_SEX_LEN+2
#define PTEID_MAX_NATIONALITY_LEN                   PTEID_NATIONALITY_LEN+2
#define PTEID_MAX_HEIGHT_LEN                        PTEID_HEIGHT_LEN+2
#define PTEID_MAX_NUMBI_LEN                         PTEID_NUMBI_LEN+2
#define PTEID_MAX_NUMNIF_LEN                        PTEID_NUMNIF_LEN+2
#define PTEID_MAX_NUMSS_LEN                         PTEID_NUMSS_LEN+2
#define PTEID_MAX_NUMSNS_LEN                        PTEID_NUMSNS_LEN+2
#define PTEID_MAX_INDICATIONEV_LEN                  PTEID_INDICATIONEV_LEN+2
#define PTEID_MAX_MRZ_LEN                           PTEID_MRZ_LEN+2    

#define PTEID_ADDR_TYPE_LEN                     2
#define PTEID_ADDR_COUNTRY_LEN                  4
#define PTEID_DISTRICT_LEN                      4
#define PTEID_DISTRICT_DESC_LEN                 100
#define PTEID_DISTRICT_CON_LEN                  8
#define PTEID_DISTRICT_CON_DESC_LEN             100
#define PTEID_DISTRICT_FREG_LEN                 12
#define PTEID_DISTRICT_FREG_DESC_LEN            100
#define PTEID_ROAD_ABBR_LEN                     20
#define PTEID_ROAD_LEN                          100
#define PTEID_ROAD_DESIG_LEN                    200
#define PTEID_HOUSE_ABBR_LEN                    20
#define PTEID_HOUSE_LEN                         100
#define PTEID_NUMDOOR_LEN                       20
#define PTEID_FLOOR_LEN                         40
#define PTEID_SIDE_LEN                          40
#define PTEID_PLACE_LEN                         100
#define PTEID_LOCALITY_LEN                      100
#define PTEID_CP4_LEN                           8    
#define PTEID_CP3_LEN                           6
#define PTEID_POSTAL_LEN                        50
#define PTEID_NUMMOR_LEN                        12
#define PTEID_ADDR_COUNTRYF_DESC_LEN            100
#define PTEID_ADDRF_LEN                         300
#define PTEID_CITYF_LEN                         100
#define PTEID_REGIOF_LEN                        100
#define PTEID_LOCALITYF_LEN                     100
#define PTEID_POSTALF_LEN                       100
#define PTEID_NUMMORF_LEN                       12

#define PTEID_MAX_ADDR_TYPE_LEN                     PTEID_ADDR_TYPE_LEN+2
#define PTEID_MAX_ADDR_COUNTRY_LEN                  PTEID_ADDR_COUNTRY_LEN+2
#define PTEID_MAX_DISTRICT_LEN                      PTEID_DISTRICT_LEN+2
#define PTEID_MAX_DISTRICT_DESC_LEN                 PTEID_DISTRICT_DESC_LEN+2
#define PTEID_MAX_DISTRICT_CON_LEN                  PTEID_DISTRICT_CON_LEN+2
#define PTEID_MAX_DISTRICT_CON_DESC_LEN             PTEID_DISTRICT_CON_DESC_LEN+2
#define PTEID_MAX_DISTRICT_FREG_LEN                 PTEID_DISTRICT_FREG_LEN+2
#define PTEID_MAX_DISTRICT_FREG_DESC_LEN            PTEID_DISTRICT_FREG_DESC_LEN+2
#define PTEID_MAX_ROAD_ABBR_LEN                     PTEID_ROAD_ABBR_LEN+2
#define PTEID_MAX_ROAD_LEN                          PTEID_ROAD_LEN+2
#define PTEID_MAX_ROAD_DESIG_LEN                    PTEID_ROAD_DESIG_LEN+2
#define PTEID_MAX_HOUSE_ABBR_LEN                    PTEID_HOUSE_ABBR_LEN+2
#define PTEID_MAX_HOUSE_LEN                         PTEID_HOUSE_LEN+2
#define PTEID_MAX_NUMDOOR_LEN                       PTEID_NUMDOOR_LEN+2
#define PTEID_MAX_FLOOR_LEN                         PTEID_FLOOR_LEN+2
#define PTEID_MAX_SIDE_LEN                          PTEID_SIDE_LEN+2
#define PTEID_MAX_PLACE_LEN                         PTEID_PLACE_LEN+2
#define PTEID_MAX_LOCALITY_LEN                      PTEID_LOCALITY_LEN+2
#define PTEID_MAX_CP4_LEN                           PTEID_CP4_LEN+2    
#define PTEID_MAX_CP3_LEN                           PTEID_CP3_LEN+2
#define PTEID_MAX_POSTAL_LEN                        PTEID_POSTAL_LEN+2
#define PTEID_MAX_NUMMOR_LEN                        PTEID_NUMMOR_LEN+2
#define PTEID_MAX_ADDR_COUNTRYF_DESC_LEN            PTEID_ADDR_COUNTRYF_DESC_LEN+2
#define PTEID_MAX_ADDRF_LEN                         PTEID_ADDRF_LEN+2
#define PTEID_MAX_CITYF_LEN                         PTEID_CITYF_LEN+2
#define PTEID_MAX_REGIOF_LEN                        PTEID_REGIOF_LEN+2
#define PTEID_MAX_LOCALITYF_LEN                     PTEID_LOCALITYF_LEN+2
#define PTEID_MAX_POSTALF_LEN                       PTEID_POSTALF_LEN+2
#define PTEID_MAX_NUMMORF_LEN                       PTEID_NUMMORF_LEN+2


#define PTEID_MAX_ID_NUMBER_LEN			64
#define PTEID_MAX_RAW_ID_LEN			1500
#define PTEID_MAX_RAW_ID_OFFSET_LEN		1503
#define PTEID_MAX_RAW_ADDR_LEN			1500
#define PTEID_MAX_PICTURE_LEN			14128
#define PTEID_MAX_CERT_LEN				2500
#define PTEID_MAX_CERT_NUMBER			10
#define PTEID_MAX_CERT_LABEL_LEN		256
#define PTEID_MAX_SIGNATURE_LEN			256  
#define PTEID_MAX_PINS					8
#define PTEID_MAX_PIN_LABEL_LEN			256                 
#define PTEID_MAX_USER_PIN_LEN			12
#define PTEID_MAX_FILE_SIZE				16384
#define PTEID_MAX_RAW_CARDINFO_LEN		512
#define PTEID_MAX_PICTURE_LEN_HEADER	111
#define PTEID_MAX_PICTUREH_LEN			(PTEID_MAX_PICTURE_LEN+PTEID_MAX_PICTURE_LEN_HEADER)
#define PTEID_MAX_CBEFF_LEN				34
#define PTEID_MAX_FACRECH_LEN			14
#define PTEID_MAX_FACINFO_LEN			20
#define PTEID_MAX_IMAGEINFO_LEN			12
#define PTEID_MAX_IMAGEHEADER_LEN		(PTEID_MAX_CBEFF_LEN+PTEID_MAX_FACRECH_LEN+PTEID_MAX_FACINFO_LEN+PTEID_MAX_IMAGEINFO_LEN)
#define PTEID_MAX_PERSO_FILE_LEN		1000
#define MAX_ID_FILE_LEN					16000  // must be at least the size of the ID file
#define MAX_SOD_FILE_LEN				4000

#define PTEID_CAP_PIN_ID			0x00

#define PTEID_AUTH_PIN_ID			0x81
#define PTEID_SIG_PIN_ID			0x82
#define PTEID_ADDRESS_PIN_ID		0x83

#define PTEID_AUTH_PIN_ID_101		0x01
#define PTEID_SIG_PIN_ID_101		0x82
#define PTEID_ADDRESS_PIN_ID_101	0x83

/* General return codes */
#define PTEID_OK							0 /* Function succeeded */
#define PTEID_E_BAD_PARAM					1 /* Invalid parameter (NULL pointer, out of bound, etc.) */
#define PTEID_E_INTERNAL					2 /* An internal consistency check failed */
#define PTEID_E_INSUFFICIENT_BUFFER	        3 /* The data buffer to receive returned data is too small for the returned data */
#define PTEID_E_KEYPAD_CANCELLED	        4 /* Input on pinpad cancelled */
#define PTEID_E_KEYPAD_TIMEOUT				5 /* Timout returned from pinpad */
#define PTEID_E_KEYPAD_PIN_MISMATCH			6 /* The two PINs did not match */
#define PTEID_E_KEYPAD_MSG_TOO_LONG			7 /* Message too long on pinpad */
#define PTEID_E_INVALID_PIN_LENGTH			8 /* Invalid PIN length */
#define PTEID_E_NOT_INITIALIZED				9 /* Library not initialized */
#define PTEID_E_UNKNOWN						10 /* An internal error has been detected, but the source is unknown */
#define PTEID_E_FILE_NOT_FOUND				11 /* Attempt to read a file has failed. */
#define PTEID_E_USER_CANCELLED				12 /* An operation was cancelled by the user. */

/* Signature validation return codes */
#define PTEID_SIGNATURE_NOT_VALIDATED			-2 /* The signature is not validated */
#define PTEID_SIGNATURE_PROCESSING_ERROR		-1 /* Error verifying the signature. */
#define PTEID_SIGNATURE_VALID					0 /* The signature is valid. */
#define PTEID_SIGNATURE_INVALID					1 /* The signature is not valid. */
#define PTEID_SIGNATURE_VALID_WRONG_RRNCERT		2 /* The signature is valid and wrong RRN certificate. */
#define PTEID_SIGNATURE_INVALID_WRONG_RRNCERT	3 /* The signature is not valid and wrong RRN certificate. */

/* PIN Types */
#define PTEID_PIN_TYPE_PKCS15	0
#define PTEID_PIN_TYPE_OS		1

/* PIN Usages */
#define PTEID_USAGE_AUTH	1
#define	PTEID_USAGE_SIGN	2

#ifndef SC_NO_ERROR
    #define SC_NO_ERROR 0
#endif

#if defined(_WIN32) || defined (__WIN32__)
	#define _USERENTRY __cdecl
#else
	#define _USERENTRY
#endif

#endif /* __PTEIDDEFINES_H__ */
