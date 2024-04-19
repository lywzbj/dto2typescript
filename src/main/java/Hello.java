package main.java;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;

public class Hello extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        Project project = anActionEvent.getData(PlatformDataKeys.PROJECT);
        // 创建对话框
        Dto2typescriptDialog dialog = new Dto2typescriptDialog();
        //对话框构建器
        DialogBuilder builder = new DialogBuilder(project);
        // 设置对话框
        builder.setCenterPanel(dialog.getRootPanel());
        builder.setTitle("dto2typescript");
        PsiFile psiFile = anActionEvent.getData(LangDataKeys.PSI_FILE);
        PsiFile originalFile = psiFile.getOriginalFile();
        // 目录类路径
        String path = originalFile.getVirtualFile().getPath();
        // 获取类型信息
        PsiClass classInfo = getTargetClassInfo(path, project);
        // 获取属性信息
        if(classInfo == null) {
            throw new RuntimeException("未找到编辑器类型信息");
        }
        // 获取类型信息
        PsiField[] allFields = classInfo.getAllFields();
        builder.show();
    }




    private PsiClass getTargetClassInfo(String path,Project project) {
        try {
            String className = getClassName(path);
            PsiClass[] psiClasses = PsiShortNamesCache.getInstance(project).getClassesByName(className, GlobalSearchScope.allScope(project));
            if(psiClasses.length > 0) {
                return psiClasses[0];
            }
            return null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }





    private Class<?> getTargetClassInfo(String path) {
        try {
            String className = getClassName(path);
            URL url = new URL(path);
            // 创建URLClassLoader对象，并传入URL对象
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url});
            // 使用Class.forName()方法获取HelloWorld类的Class实例
            Class<?> helloWorldClass = Class.forName(className, true, urlClassLoader);
            // 输出获取到的Class实例的信息
            System.out.println("Class name: " + className);
            return helloWorldClass;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getClassName(String path) throws FileNotFoundException {
        Objects.isNull(path);
        File file = new File(path);
        if(!file.exists()) {
            throw new FileNotFoundException("找不到类文件");
        }

        String name = file.getName();
        if(!name.endsWith(".java")) {
            throw new FileNotFoundException("该文件不是Java类文件");
        }
        int i = name.indexOf(".");
        return name.substring(0,i);
    }



















}
