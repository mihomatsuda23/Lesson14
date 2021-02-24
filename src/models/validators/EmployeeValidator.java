package models.validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import utils.DBUtil;

public class EmployeeValidator {
	public static List<String> validate(Enployee e, Boolean codeDuplicateCheckFlag, Boolean passwordCheckFlag){
		List<String> errors=new ArrayList<String>();

		String code_error=validateCode(e.getCode(),codeDuplicateCheckFlag);
		if(!code_error.equals("")){
			errors.add(code_error);
		}

		String name_error=validateName(e.getName());
		if(!name_error.equals("")){
			errors.add(name_error);
		}

		String password_error=validateName(e.getPassword(),passwordCheckFlag);
		if(!name_error.equals("")){
			errors.add(password_error);
		}

		return errors;
	}

	//社員番号
	private static String  validateCode(String code,Boolean codeDuplicateCheckFlag){
		//必須入力チェック
		if(code==null||code.equals("")){
			return "社員番号を入力してください。";
		}

		//すでに登録されている社員番号との重複チェック
		if(codeDuplicateCheckFlag){
			EntityManager em=DBUtil.createEntityManager();
			long employees_count=(long)em.createNamedQuery("checkRegisteredCode",Long.class).setParameter("code",code).getSingleResult();
			em.close();
			if(employees_count>0){
				return "入力された社員番号の情報はすでに存在しています。";
			}
		}

	return "";

	}

	//パスワードの必須入力チェック
	private static String validatePassword(String password, Boolean passwordCheckFlag){
		//パスワードを変更する場合のみ実行
		if(passwordCheckFlag && (password==null || password.equals(""))){
			return "パスワードを入力してください。";
		}

		return "";
	}
}
