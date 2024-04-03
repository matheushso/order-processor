package br.com.processor.order.unit.service;

import br.com.processor.order.exception.AuthException;
import br.com.processor.order.service.AuthService;
import br.com.processor.order.unit.constants.ConstantsTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    private static final String AUTH_MSG_ERROR = "O token JWT informado é invalido.";
    private static final String AUTH_WITHOUT_SELLER_ID_MSG_ERROR = "Não foi informado sellerId no JWT, favor informar.";
    private static final String TOKEN_JWT_NOT_START_WITH_BEARER = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzZWxsZXJJZCI6IjY2MDgxMDg0OGE3NTRlMGMzMmQ2Y2EyYSJ9.Me30gMGf-T15zr6FoPRLc6QnPT2KDn7JFA4Rg0Jte0c";
    private static final String TOKEN_JWT_WITH_SELLER_ID = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzZWxsZXJJZCI6IjY2MDgxMDg0OGE3NTRlMGMzMmQ2Y2EyYSJ9.Me30gMGf-T15zr6FoPRLc6QnPT2KDn7JFA4Rg0Jte0c";
    private static final String TOKEN_JWT_WITH_SELLER_ID_BLANK = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzZWxsZXJJZCI6IiJ9.1KMKiemY7VbvGfGBnCw9_7_pLKapwGCmfr50mlP50fw";
    private static final String TOKEN_JWT_WITHOUT_SELLER_ID = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIn0.CPfClgve7vk0hugyn3DzCGGBflgBBNlmg2mk9Mu4_4I";

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(authService, "secretKey", "SsyucmjONjVmLtMbQUjIJFfjEF83RfDUU19zUHIYRUg=");
    }

    @Test
    public void validateAuthAndDecodeWhenJwtIsNullReturnAuthException() {
        AuthException ex = assertThrows(AuthException.class, () -> authService.validateAuthAndDecode(null));

        assertEquals(AUTH_MSG_ERROR, ex.getMessage());
    }

    @Test
    public void validateAuthAndDecodeWhenJwtIsBlankReturnAuthException() {
        AuthException ex = assertThrows(AuthException.class, () -> authService.validateAuthAndDecode(" "));

        assertEquals(AUTH_MSG_ERROR, ex.getMessage());
    }

    @Test
    public void validateAuthAndDecodeWhenJwtNotStartWithBearerButValidReturnAuth() {
        String auth = authService.validateAuthAndDecode(TOKEN_JWT_NOT_START_WITH_BEARER);

        assertEquals(auth, ConstantsTests.SELLER_ID);
    }

    @Test
    public void validateAuthAndDecodeWhenJwtIsValidReturnAuth() {
        String auth = authService.validateAuthAndDecode(TOKEN_JWT_WITH_SELLER_ID);

        assertEquals(auth, ConstantsTests.SELLER_ID);
    }

    @Test
    public void validateAuthAndDecodeWhenJwtIsValidWithoutSellerIdReturnError() {
        AuthException ex = assertThrows(AuthException.class, () -> authService.validateAuthAndDecode(TOKEN_JWT_WITHOUT_SELLER_ID));

        assertEquals(AUTH_WITHOUT_SELLER_ID_MSG_ERROR, ex.getMessage());
    }

    @Test
    public void validateAuthAndDecodeWhenJwtIsValidWithSellerIdBlankReturnError() {
        AuthException ex = assertThrows(AuthException.class, () -> authService.validateAuthAndDecode(TOKEN_JWT_WITH_SELLER_ID_BLANK));

        assertEquals(AUTH_WITHOUT_SELLER_ID_MSG_ERROR, ex.getMessage());
    }
}
